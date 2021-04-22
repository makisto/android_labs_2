package com.example.lab_0_tests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * Лабораторная работа №1
 *
 * @author Ilya Martasov
 * @version 4.0.1
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Функция получает на вход два числа и возвращает большее из них
     *
     * @param a 1 число
     * @param b 2 число
     * @return возвращает большее из данных чисел
     */
    public int max(int a, int b)
    {
        return a > b ? a : b;
    }

    /**
     * Функция получает на вход два числа и возвращает меньшее из них
     *
     * @param a 1 число
     * @param b 2 число
     * @return возвращает меньшее из данных чисел
     */
    public int min(int a, int b)
    {
        return a > b ? b : a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}