/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package br.com.edsonjuniorcoder.exemplonewfixedthreadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jerry
 */
public class ExemploNewFixedThreadPool {

    public static void main(String[] args) {

        //Cria objeto controlador da thread
        int qtdThread = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(1000);

        List<String> list = new ArrayList<String>();

        //Adiciona linhas na Lista
        for (int i = 1; i <= 10000; i++) {
            list.add("linha " + i);

        }

        for (int i = 0; i < qtdThread; i++) {
            int target = i;
            System.out.println("target: " + target);
            executor.submit(new Runnable() {

                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {

                        synchronized (list) {

                            if (list.size() >= 1) {
                                String linha = list.get(0);
                                System.out.println("thread " + target + " " + linha);
                                list.remove(0);
                            } else {
                                Thread.currentThread().interrupt();
                                break;

                            }

                        }

                    }

                    System.out.println("saiu while " + target);
                }
            });

        }

        executor.shutdown(); //Finaliza executor

        //Thread responsável para verificar termino das execuções
        new Thread(new Runnable() {
            public void run() {

                while (!executor.isTerminated()) {
                }

                System.out.println("teste");
                System.out.println("executor.isTerminated(): " + executor.isTerminated());
                System.out.println("executor.isShutdown(): " + executor.isShutdown());

            }
        }).start();

    }
}
