package com.example.ggavi.registeration.ahn1;

//import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.ggavi.registeration.R;
import com.example.ggavi.registeration.phu1.CustomConfirmDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

// NoticeListAdapter.java를 가져와서 수정
public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courseList;      // Course가 들어가는 리스트를 만들어줌
    private Fragment parent;              // (12) 추가

    // (13) 해당 서버 프로그램을 이용해서 코스가 중복되는지 체크
    private String userID = MainActivity.userID;  // MainActivity에 있는 public 형태의 userID를 가져와서 해당 사용자의 아이디를 저장
    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList;           // courseID가 중복되는지 검사하기 위해 courseID가 들어가는 리스트

    // (19) 코스 갯수 제한 기능 (근데 지금은 무조건 코스를 1개만 넣도록 바꿔놔서 사실상 필요없긴 함)
    public static int totalCredit = 0;

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent)  //자신을 불러낸 부모 Fragment를 담을 수 있도록 한다.
    {
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;

        // 생성자에 다음과 같이 해당 스케쥴 함수를 초기화
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        new BackgroundTask().execute();

        // (19)
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int i) //i는 position
    {
        return courseList.get(i);
    }

    @Override
    public long getItemId(int i) //i는 position
    {
        return i;
    }

    @Override  //i는 position, viewGroupParent는 처음에 parent로 자동완성(자동생성) 되서 밑에 있는 parent와 중복되서 에러 생겼음 (그래서 고침)
    public View getView(final int i, View convertView, ViewGroup viewGroupParent)
    {
        // 하나의 View로 만들어 줄 수 있도록 한다.
        View v = View.inflate(context, R.layout.course, null);


        // course라는 레이아웃에 있는 모든 원소가 하나의 변수로써 자리잡게 되었다.
        // 근데 레이아웃에서 다 안보이게 처리해놔서 현재 대부분이 더미데이터로 남아 있다.
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);



        courseTitle.setText(courseList.get(i).getCourseTitle());

        // 현재 코스에서 강의 ID값을 태그로 삼을 수 있도록 한다.
        v.setTag(courseList.get(i).getCourseID());


        // 리턴하기 전에 버튼 이벤트 추가 (코스를 추가하는 부분)
        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 현재 코스를 추가할 수 있는지 타당성을 검증
                // 현재 새롭게 추가할려는 코스에 시간표를 넣음으로써 타당성을 검증할 수 있도록 함
                // 근데 현재 그 시간표를 수정해서 값이 1개만 들어가게 만듬
                boolean validate = false;
                validate = schedule.validate(courseList.get(i).getCourseTime());

                // userID: 메인 액티비티에 있는 회원의 정보를 가져옴
                // String userID = MainActivity.userID; (지금은 삭제)

                // 기존에는 그냥 추가하는 명령만 수행했지만,
                // 이제는 중복되거나 이미 존재하는 강의인 경우 추가할 수 없도록 막음
                if (!alreadyIn(courseIDList, courseList.get(i).getCourseID())) {
                    // 만약 자기가 신청했던 코스ID 속에서 현재 신청한 코스가 ID에 포함되어 있다면 이미 신청한 코스이므로 신청할 수 없도록 한다.
                    new CustomConfirmDialog().showConfirmDialog(new CourseFragment().getContext2(),"이미 등록되어 있는 코스입니다.",true);
                }



                // 즉 중복되었다면
                else if (validate == false) {
                    // 월요일[1]인 코스1과 월요일[1]인 코스2를 동시에 신청 못하게 막음
                    // 현재 DB에 모든 코스를 월요일[1]로 넣어놔서 값을 1개 밖에 못 넣는다 ㅋㅋ
                    // 만약 자기가 신청했던 코스ID 속에서 현재 신청한 코스가 ID에 포함되어 있다면 이미 신청한 코스이므로 신청할 수 없도록 한다.
                    new CustomConfirmDialog().showConfirmDialog(new CourseFragment().getContext2(),"이미 다른 코스가 등록되어 있습니다.",true);
                }


                // 둘 다 아니면 정상적으로 추가된다.
                else
                {
                    // RegisterActivity.java에서 가져와서 복붙 (12)
                    // 정상적으로 ID 값을 입력했을 경우 중복체크 시작
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // 해당 웹사이트에 접속한 뒤 특정한 response(응답)을 다시 받을 수 있도록 한다
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                // 만약 사용할 수 있는 아이디라면
                                if (success) {
                                    new CustomConfirmDialog().showConfirmDialog(new CourseFragment().getContext2(),"코스가 등록되었습니다.",true);

                                    // (13)이제 코스가 추가될 때마다 코드 안에서도 강의를 직접 추가시켜줘야 되기 때문에 추가해준다.
                                    courseIDList.add(courseList.get(i).getCourseID());        // 회당 코스 번호를 등록
                                    schedule.addSchedule(courseList.get(i).getCourseTime());  // 스케쥴 추가

                                    // (19)
                                    totalCredit += courseList.get(i).getCourseCredit();
                                }

                                // 중복체크 실패 (사용할 수 없는 아이디)
                                else {
                                    new CustomConfirmDialog().showConfirmDialog(new CourseFragment().getContext2(),"코스 등록에 실패하였습니다.",true);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    // 실질적으로 접속할 수 있도록 생성자를 통해 객체를 만든다. (유저 ID, responseListener)
                    // 그리고 어떤 회원이 어떤 코스를 듣는다는 스케쥴 데이터는 스케쥴 DB에 넣어야 한다.
                    // AddRequest.java라는 파일을 만들어야 한다.
                    AddRequest addRequest = new AddRequest(userID, courseList.get(i).getCourseID() + "", responseListener);  // + ""를 붙이면 문자열 형태로 바꿈
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }
            }
        });

        return v;
    }

    // 데이터베이스에 접속할 수 있도록 만든 함수
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;  //우리가 접속할 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            // 스케쥴 리스트를 검사할 수 있도록 userID를 넣어줌
            try
            {
                target = "http://ggavi2000.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");  //해당 웹 서버에 접속
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                // 해당 서버에 접속할 수 있도록 URL을 커넥팅 한다.
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                // 넘어오는 결과값을 그대로 저장
                InputStream inputStream = httpURLConnection.getInputStream();

                // 해당 inputStream에 있던 내용들을 버퍼에 담아서 읽을 수 있도록 해줌
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // 이제 temp에 하나씩 읽어와서 그것을 문자열 형태로 저장
                String temp;
                StringBuilder stringBuilder = new StringBuilder();

                // null 값이 아닐 때까지 계속 반복해서 읽어온다.
                while ((temp=bufferedReader.readLine()) != null)
                {
                    // temp에 한줄씩 추가하면서 넣어줌
                    stringBuilder.append(temp + "\n");
                }

                // 끝난 뒤 닫기
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();  //인터넷도 끊어줌
                return stringBuilder.toString().trim();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        // 공지사항 코드를 그대로 복사 붙여넣기해서 만들어서 주석까지 딸려옴(...)
        // 해당 결과를 처리할 수 있는 onPostExecute()
        @Override
        public void onPostExecute(String result) {
            try {
                // 해당 결과(result) 응답 부분을 처리
                JSONObject jsonObject = new JSONObject(result);

                // response에 각각의 공지사항 리스트가 담기게 됨
                JSONArray jsonArray = jsonObject.getJSONArray("response");  //아까 변수 이름

                int count = 0;
                String courseProfessor;
                String courseTime;
                int courseID;

                // (19)
                totalCredit = 0;

                // 해당 홈페이지를 탐색한 뒤 나온 결과를 분석해서 파싱한 뒤에
                while (count < jsonArray.length())
                {
                    // 현재 배열의 원소값을 저장
                    JSONObject object = jsonArray.getJSONObject(count);

                    // 공지사항의 Content, Name, Date에 해당하는 값을 가져와라는 뜻
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");

                    // (19)
                    totalCredit += object.getInt("courseCredit");

                    // 리스트에 추가해줌
                    // 현재 해당 사용자가 가지고 있는 모든 시간표 데이터에 있는 코스ID가
                    // 코스ID 리스트에 담기게 되고, 스케쥴 또한 마찬가지로 들어가게 된다.
                    courseIDList.add(courseID);
                    schedule.addSchedule(courseTime);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 현재 해당 코스의 아이디에 해당하는 강의 데이터가 이미 들어가 있는 상태라면 체크해주는 메소드
    public boolean alreadyIn(List<Integer> courseIDList, int item)
    {
        // 모든 코스 리스트의 아이디를 돌면서 현재 추가하려는 id값과 일치하는게 하나라도 있으면 false
        for(int i = 0; i<courseIDList.size(); i++)
        {
            if(courseIDList.get(i) == item)
            {
                return false;
            }
        }

        return true;
    }
}

