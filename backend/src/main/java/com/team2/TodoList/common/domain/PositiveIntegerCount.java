package com.team2.TodoList.common.domain;

public class PositiveIntegerCount {

    private int count;

    public PositiveIntegerCount() {
        this.count = 0;
    }

    public PositiveIntegerCount(int count) {
        this.count = count;
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        if(this.count <= 0) {
            return;
        }
        this.count--;
    }

    public int getCount() {
        return count;
    }
}
