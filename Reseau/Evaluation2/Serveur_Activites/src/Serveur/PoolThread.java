package Serveur;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

class PoolThread {
    private final int nbThread;
    private final PoolWorker[] threads;
    private final LinkedList queue;
    
    public PoolThread(int nb) {
        this.nbThread = nb;
        queue = new LinkedList();
        threads = new PoolWorker[this.nbThread];
        
        for(int i=0; i<this.nbThread; i++){
            threads[i] = new PoolWorker();
            threads[i].start();
            System.out.println("Thread " + i + " en attente.");
        }
    }

    void assign(Runnable traitementPacket) {
        synchronized(queue){
            queue.add(traitementPacket);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread{
        public void run() {
            Runnable r;
            while(true){
                synchronized(queue){
                    while(queue.isEmpty()){
                        try {
                            queue.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PoolThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    r = (Runnable) queue.removeFirst();
                }
                try{
                    r.run();
                }catch(RuntimeException ex){
                    System.out.println("Erreur de pool: " + ex.getMessage());
                }
            }
        }
    }
}
