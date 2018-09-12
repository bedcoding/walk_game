package com.example.ggavi.registeration.ahn1;

public class Course {

    // 수강신청 소스를 가져와서 길찾기 소스로 수정
    // 현재 대부분이 더미데이터로 남아 있지만 DB쿼리문이랑 PHP문까지 손대야 해서 내버려둠
    int courseID;               // 고유 번호
    String courseUniversity;    // 학부 혹은 대학원
    String courseYear;          // 해당 년도
    String courseTerm;          // 해당 학기
    String courseArea;          // 강의 영역
    String courseMajor;         // 해당 학과
    String courseGrade;         // 해당 학년
    String courseTitle;         // 강의 제목
    int courseCredit;           // 강의 학점
    int courseDivide;           // 강의 분반
    int coursePersonnel;        // 강의 제한 인원
    String courseProfessor;     // 강의 교수
    String courseTime;          // 강의 시간대
    String courseRoom;          // 강의실
    int courseRival;            // (18)경쟁자의 숫자 (수강신청하려는 사람들의 숫자)


    // (18) 생성자: statistics.xml에서 필요한 요소들
    public Course(int courseID, String courseTitle, int courseDivide, String courseGrade, int coursePersonnel, int courseRival) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseDivide = courseDivide;
        this.courseGrade = courseGrade;
        this.coursePersonnel = coursePersonnel;
        this.courseRival = courseRival;
    }

    public Course(int courseID, String courseTitle, int courseDivide, String courseGrade, int coursePersonnel, int courseRival, int courseCredit) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseDivide = courseDivide;
        this.courseGrade = courseGrade;
        this.coursePersonnel = coursePersonnel;
        this.courseRival = courseRival;
        this.courseCredit = courseCredit;
    }

    // (21) 순위 출력할 때 만든 생성자
    public Course(int courseID, String courseGrade, String courseTitle, int courseCredit, int courseDivide, int coursePersonnel, String courseTime, String courseProfessor) {
        this.courseID = courseID;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseTime = courseTime;
        this.courseProfessor = courseProfessor;
    }

    public int getCourseRival() {
        return courseRival;
    }

    public void setCourseRival(int courseRival) {
        this.courseRival = courseRival;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseUniversity() {
        return courseUniversity;
    }

    public void setCourseUniversity(String courseUniversity) {
        this.courseUniversity = courseUniversity;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseArea() {
        return courseArea;
    }

    public void setCourseArea(String courseArea) {
        this.courseArea = courseArea;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public int getCourseDivide() {
        return courseDivide;
    }

    public void setCourseDivide(int courseDivide) {
        this.courseDivide = courseDivide;
    }

    public int getCoursePersonnel() {
        return coursePersonnel;
    }

    public void setCoursePersonnel(int coursePersonnel) {
        this.coursePersonnel = coursePersonnel;
    }

    public String getCourseProfessor() {
        return courseProfessor;
    }

    public void setCourseProfessor(String courseProfessor) {
        this.courseProfessor = courseProfessor;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
    }


    // Generate로 자동으로 생성자 만듬 (Alt + Insert)
    public Course(int courseID, String courseUniversity, String courseYear, String courseTerm, String courseArea, String courseMajor, String courseGrade, String courseTitle, int courseCredit, int courseDivide, int coursePersonnel, String courseProfessor, String courseTime, String courseRoom) {
        this.courseID = courseID;
        this.courseUniversity = courseUniversity;
        this.courseYear = courseYear;
        this.courseTerm = courseTerm;
        this.courseArea = courseArea;
        this.courseMajor = courseMajor;
        this.courseGrade = courseGrade;
        this.courseTitle = courseTitle;
        this.courseCredit = courseCredit;
        this.courseDivide = courseDivide;
        this.coursePersonnel = coursePersonnel;
        this.courseProfessor = courseProfessor;
        this.courseTime = courseTime;
        this.courseRoom = courseRoom;
    }
}
