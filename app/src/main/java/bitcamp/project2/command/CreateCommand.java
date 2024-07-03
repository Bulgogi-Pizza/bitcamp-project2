package bitcamp.project2.command;

import bitcamp.project2.util.Print;
import bitcamp.project2.util.PromptTodo;
import bitcamp.project2.vo.Priority;
import bitcamp.project2.vo.Repeat;
import bitcamp.project2.vo.Term;
import bitcamp.project2.vo.Todo;
import java.util.Calendar;
import java.util.LinkedList;

public class CreateCommand {

    public static void createTodo() {
        Print.printTitle("Todo 생성");

        String todoTitle;

        // title 입력
        while (true) {
            todoTitle = PromptTodo.input("Todo 제목 입력 (최대 20Bytes) >>");

            if (todoTitle.getBytes().length > Todo.MAX_LENGTH_TITLE) {
                System.out.println("제목이 너무 깁니다.");
            } else {
                break;
            }
        }

        // deadline 입력
        Print.printTitle("기한");
        Calendar today = Calendar.getInstance();
        Calendar deadline = PromptTodo.inputCalendar(today);

        // storage 입력
        Print.printTitle("보관함");
        int storageSize = Todo.getStorageList().size();
        for (int i = 0; i < storageSize; i++) {
            String storageCandidate = Todo.getStorageList().get(i);
            System.out.println(i + 1 + ". " + storageCandidate);
        }
        int storageIndex = PromptTodo.inputIntWithRange(1, storageSize, "저장소 선택 >>") - 1;

        // priority 입력
        Print.printTitle("우선 순위");
        int prioritySize = Priority.values().length;
        for (int i = 0; i < Priority.values().length; i++) {
            System.out.println(i + 1 + ". " + Priority.values()[i].getName());
        }
        int priorityIndex = PromptTodo.inputIntWithRange(1, prioritySize, "우선순위 선택 >>") - 1;
        Priority priority = Priority.values()[priorityIndex];

        // repeat 여부
        Print.printTitle("반복");
        System.out.println("1. 반복 O");
        System.out.println("2. 반복 X");

        int repeatYesOrNo = PromptTodo.inputIntWithRange(1, 2, "반복 여부 >>");
        boolean repeat = repeatYesOrNo == 1 ? true : false;

        Term term = Term.DAY;
        if (repeat) {
            // 반복 기간 설정
            int periodSize = Term.values().length;
            for (int i = 0; i < periodSize; i++) {
                System.out.println(i + 1 + ". " + Term.values()[i].getName());
            }

            int periodIndex = PromptTodo.inputIntWithRange(1, periodSize, "반복 기간 선택 >>") - 1;
            term = Term.values()[periodIndex];

        } else {
            term = Term.YEAR;
        }

        Repeat repeatSet = new Repeat(repeat, term);

        // tag 지정
        Print.printTitle("태그");
        LinkedList<String> tags = new LinkedList<>();
        String inputString;
        while (true) {
            inputString = PromptTodo.input("태그 입력 [0 = 입력 종료] >>");

            if (inputString.equals("0")) {
                break;
            } else {
                tags.add(inputString);
            }
        }

        Todo todo = new Todo(todoTitle, deadline, storageIndex, priority, repeatSet, tags);
        TodoCommand.todos.add(todo);
    }

}
