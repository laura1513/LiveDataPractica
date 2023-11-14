package com.example.livedatapractica;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Tiempo {
    interface TiempoListener {
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> cambiando;
    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarTiempo(new TiempoListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararTiempo();
        }
    };

    void iniciarTiempo(TiempoListener tiempoListener) {
        if (cambiando == null || cambiando.isCancelled()) {
            cambiando = scheduler.scheduleAtFixedRate(new Runnable() {
                int t;
                int repetir;
                @Override
                public void run() {
                    if (repetir < 0){
                        repetir = random.nextInt(3) + 3;
                        t = random.nextInt(5) + 1;
                    }
                    tiempoListener.cuandoDeLaOrden("TIEMPO" + t + ":" + (repetir == 0 ? "CAMBIO" : repetir));
                    repetir--;
                }
            }, 0, 1, SECONDS);
        }
    }
    void pararTiempo() {
        if (cambiando != null){
            cambiando.cancel(true);
        }
    }
}
