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
public class Cliente {
                                
int tiempoServicio;             // el tiempoServicio almacena el tiempo en el que el servidor va a atender al cliente          
int tiempoLlegadaCola;          // El tiempo llegada cola almacena el tiempo en el que el servidor va a llegar a la cola
int costo;                      // El costo almacena el costo por unidad de tiempo que cuesta el cliente
int tiempoEnCola;


public Cliente(int tiempoLlegadaCola, int tiempoServicio, int costo) {
        
        this.tiempoLlegadaCola = tiempoLlegadaCola;
        this.tiempoServicio = tiempoServicio;
       this.tiempoEnCola=0;
        this.costo=costo;
        
        
    }


public int getTiempoSistema (){
    
    return tiempoServicio-tiempoLlegadaCola;
}



    
  
}
