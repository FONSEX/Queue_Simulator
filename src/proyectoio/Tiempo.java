/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoio;

/**
 *
 * @author juan_
 */
public class Tiempo {
     
     int minimo;             // valor minimo de la lista de numeros aleatorios independientes
     int maximo;            // valor maximo de la lista de numeros aleatorios independientes
     int valor;            // valor en minutos del tiempo 
     float prob;          // prob de que este tiempo suceda
     
    public Tiempo(int valor, float probabilidad, int min, int max) {
        this.valor = valor;
        this.prob = probabilidad;
        this.minimo = min;
        this.maximo = max;
    }
  
    public int getValor() {
        return valor;
    }
    
    public float getProbabilidad() {
        return prob;
    }
  
}
