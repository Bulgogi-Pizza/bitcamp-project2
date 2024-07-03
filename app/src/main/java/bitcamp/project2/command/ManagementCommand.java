package bitcamp.project2.command;


import bitcamp.project2.util.Print;
import bitcamp.project2.util.PromptTodo;
import bitcamp.project2.vo.Priority;
import bitcamp.project2.vo.Repeat;
import bitcamp.project2.vo.Term;
import bitcamp.project2.vo.Todo;
import java.util.Calendar;
import java.util.LinkedList;

public class ManagementCommand {

    public static void management() {
        String[] manageMenus = {"Todo 관리", "보관함 관리", "이전"};

        while (true) {
            Print.printTitle("관리");
            Print.printMenus(manageMenus);

            int menuNo = PromptTodo.inputInt("메인/관리 >>");

            switch (menuNo) {
                case 1:
                    manageTodoList();
                    break;
                case 2:
                    StorageCommand.manageStorage();
                    break;
                case 0:
                    return;
                default:
                    Print.printSystem("올바른 메뉴를 입력하세요.");
            }
        }
    }

    public static void manageTodoList() {
        String[] manageTodoMenus = {"완료", "삭제", "수정", "이전"};

        while (true) {
            Print.printTitle("Todo 관리");
            Print.printMenus(manageTodoMenus);

            int menuNo = PromptTodo.inputInt("메인/관리/Todo 관리 >>");

            switch (menuNo) {
                case 1:
                    completeTodo();
                    break;
                case 2:
                    deleteTodo();
                    break;
                case 3:
                    updateTodo();
                    break;
                case 0:
                    return;
                default:
                    Print.printSystem("올바른 메뉴를 입력하세요.");
            }
        }
    }

    public static void completeTodo() {
        Print.printTitle("완료 상태 변경");
        Print.printTodoList(TodoCommand.todos);
        int todoSize = TodoCommand.todos.size();

        int todoNo = PromptTodo.inputIntWithRange(0, todoSize, "완료 상태 변경 No >>");

        Todo todo = TodoCommand.todos.getByNo(todoNo);
        int todoIndex = TodoCommand.todos.ofIndex(todo);
        boolean complete = todo.isComplete();
        TodoCommand.todos.get(todoIndex).setComplete(!complete);
    }

    public static void deleteTodo() {
        Print.printTitle("Todo 삭제");
        Print.printTodoList(TodoCommand.todos);
        int todoSize = TodoCommand.todos.size();

        int todoNo = PromptTodo.inputIntWithRange(0, todoSize, "삭제 Todo No >>");

        Todo todo = TodoCommand.todos.getByNo(todoNo);
        int todoIndex = TodoCommand.todos.ofIndex(todo);
        TodoCommand.todos.remove(todoIndex);
    }

    public static void updateTodo() {
        Print.printTitle("Todo 변경");
        Print.printTodoList(TodoCommand.todos);
        int todoSize = TodoCommand.todos.size();

        int todoNo = PromptTodo.inputIntWithRange(0, todoSize, "변경 Todo No >>");

        Todo todo = TodoCommand.todos.getByNo(todoNo);
        int todoIndex = TodoCommand.todos.ofIndex(todo);

        // title 입력
        String todoTitle = PromptTodo.input("Todo 제목 입력 (" + todo.getTitle() + ") >>");
        todo.setTitle(todoTitle);

        // deadline 입력
        Print.printTitle("기한");
        Calendar beforeDeadline = todo.getDeadline();
        Calendar deadline = PromptTodo.inputCalendar(beforeDeadline);
        todo.setDeadline(deadline);

        // storage 입력
        Print.printTitle("보관함");
        int storageSize = Todo.getStorageList().size();
        for (int i = 0; i < storageSize; i++) {
            String storageCandidate = Todo.getStorageList().get(i);
            System.out.println(i + 1 + ". " + storageCandidate);
        }
        int storageIndex =
            PromptTodo.inputIntWithRange(1, storageSize, "저장소 선택 (" + todo.getStorage() + ")>>")
                - 1;
        todo.setStorageIndex(storageIndex);

        // priority 입력
        Print.printTitle("우선 순위");
        int prioritySize = Priority.values().length;
        for (int i = 0; i < Priority.values().length; i++) {
            System.out.println(i + 1 + ". " + Priority.values()[i].getName());
        }
        int priorityIndex =
            PromptTodo.inputIntWithRange(1, prioritySize, "우선순위 선택 (" + todo.getPriority() + ")>>")
                - 1;
        Priority priority = Priority.values()[priorityIndex];
        todo.setPriority(priority);

        // repeat 여부
        Print.printTitle("반복");
        System.out.println("1. 반복 O");
        System.out.println("2. 반복 X");

        int repeatYesOrNo = PromptTodo.inputIntWithRange(1, 2,
            "반복 여부 (" + (todo.getRepeat().repeat ? "반복 O" : "반복 X") + ")>>");
        boolean repeat = repeatYesOrNo == 1;

        Term term = Term.DAY;
        if (repeat) {
            // 반복 기간 설정
            int periodSize = Term.values().length;
            for (int i = 0; i < periodSize; i++) {
                System.out.println(i + 1 + ". " + Term.values()[i].getName());
            }

            int periodIndex =
                PromptTodo.inputIntWithRange(1, periodSize,
                    "반복 기간 선택 (" + todo.getRepeat().repeatTerm + ")>>") - 1;
            term = Term.values()[periodIndex];

        } else {
            term = Term.YEAR;
        }

        Repeat repeatSet = new Repeat(repeat, term);
        todo.setRepeat(repeatSet);

        // tag 지정
        Print.printTitle("기존 태그");
        for (int i = 0; i < todo.getTags().size(); i++) {
            System.out.println(i + 1 + ". " + todo.getTags().get(i));
        }
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
        todo.setTags(tags);

        TodoCommand.todos.set(todoIndex, todo);
    }


}
