package bitcamp.project2.command;

import bitcamp.project2.util.Ansi;
import bitcamp.project2.util.Print;
import bitcamp.project2.util.PromptTodo;
import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;
import java.util.LinkedList;

public class ReadCommand {

    public static void readTodo() {
        String[] manageMenus = {"전체 조회", "태그 검색", "기한 기준 조회", "완료/미완료 조회", "이전"};

        while (true) {
            Print.printTitle("관리");
            Print.printMenus(manageMenus);

            int menuNo = PromptTodo.inputInt("메인/관리 >>");

            switch (menuNo) {
                case 1:
                    Print.printTodoListByComplete(TodoCommand.todos);
                    break;
                case 2:
                    searchTags();
                    break;
                case 3:
                    Print.printTodoListByDate(TodoCommand.todos);
                    break;
                case 4:
                    Print.printTodoListByPriority(TodoCommand.todos);
                    break;
                case 0:
                    return;
                default:
                    Print.printSystem("올바른 메뉴를 입력하세요.");
            }
        }
    }

    public static void searchTags() {
        String[] searchTagsMenus = {"태그 목록 보기", "직접 검색", "이전"};
        Print.printTitle("태그 검색");

        Print.printMenus(searchTagsMenus);

        int menuNo = PromptTodo.inputIntWithRange(0, 2, "태그 검색 방법 >>");

        String searchTag = "";

        LinkedList<String> allTags = new LinkedList<String>();
        switch (menuNo) {
            case 1:
                for (int i = 0; i < TodoCommand.todos.size(); i++) {
                    Todo todo = TodoCommand.todos.get(i);
                    for (int j = 0; j < todo.getTags().size(); j++) {
                        if (!allTags.contains(todo.getTags().get(j))) {
                            allTags.add(todo.getTags().get(j));
                        }
                    }
                }

                Print.printTitle("태그 목록");
                for (int i = 0; i < allTags.size(); i++) {
                    System.out.println(i + 1 + ". " + allTags.get(i));
                }
                System.out.println(Ansi.RED.getName() + "0. 이전" + Ansi.INIT.getName());

                int selectNum = PromptTodo.inputIntWithRange(0, allTags.size(), "태그 선택 >>") - 1;

                searchTag = allTags.get(selectNum);

                break;
            case 2:
                searchTag = PromptTodo.input("태그 검색 >>");
                break;
            case 0:
                return;
            default:
                System.out.println("올바른 메뉴를 입력하세요.");
                break;
        }

        TodoList todoList = new TodoList();

        for (int i = 0; i < TodoCommand.todos.size(); i++) {
            if (TodoCommand.todos.get(i).getTags().contains(searchTag)) {
                todoList.add(TodoCommand.todos.get(i));
            }
        }

        Print.printTodoList(todoList);
    }
}
