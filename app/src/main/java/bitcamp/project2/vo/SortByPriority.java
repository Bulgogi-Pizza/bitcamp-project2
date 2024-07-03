package bitcamp.project2.vo;

import java.util.Collections;

public class SortByPriority implements Sorter {

    public TodoList sort(TodoList todoList) {
        for (int i = 0; i < todoList.size(); i++) {
            for (int j = i + 1; j < todoList.size(); j++) {
                Todo todoA = todoList.get(i);
                Todo todoB = todoList.get(j);
                if (todoA.getPriority().getName() < todoB.getPriority().getName()) {
                    int tempNo = todoA.getNo();
                    todoA.setNo(todoB.getNo());
                    todoB.setNo(tempNo);
                    Collections.swap(todoList, i, j);
                }
            }
        }

        return todoList;
    }
}
