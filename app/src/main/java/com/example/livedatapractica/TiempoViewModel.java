package com.example.livedatapractica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import kotlin.jvm.functions.Function1;

public class TiempoViewModel extends AndroidViewModel {
    Tiempo tiempo;
    LiveData<Integer> tiempoLiveData;
    LiveData<String> repeticionLiveData;
    public TiempoViewModel(@NonNull Application application) {
        super(application);
        tiempo = new Tiempo();
        tiempoLiveData = Transformations.switchMap(tiempo.ordenLiveData, new Function1<String, LiveData<Integer>>() {
            String tiempoAnterior;

            @Override
            public LiveData<Integer> invoke(String orden) {
                String t = orden.split(":")[0];
                if (!t.equals(tiempoAnterior)) {
                    tiempoAnterior = t;
                    int imagen;
                    switch (t) {
                        case "TIEMPO1":
                        default:
                            imagen = R.drawable.sol;
                            break;
                        case "TIEMPO2":
                            imagen = R.drawable.lluvia;
                            break;
                        case "TIEMPO3":
                            imagen = R.drawable.nube;
                            break;
                        case "TIEMPO4":
                            imagen = R.drawable.viento;
                            break;
                    }
                    return new MutableLiveData<>(imagen);
                }
                return null;
            }
        });
        repeticionLiveData = Transformations.switchMap(tiempo.ordenLiveData, new Function1<String, LiveData<String>>() {
            @Override
            public LiveData<String> invoke(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }
        });
    }
    LiveData<Integer> obtenerTiempo(){
        return tiempoLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return repeticionLiveData;
    }
}
