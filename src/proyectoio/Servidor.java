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
public class Servidor {
    
    int costo;                  // costo del servidor
    int tiempoUtilizacion;      // todo el tiempo que sera utilizado
    int cantidadVecesUsado;     // cantidad de veces que ha sido utilizado
    Cliente cliente;            // guarda al cliente que esta siendo atendido
    boolean empty;              // estado para saber si el servidor esta libre o no
    int DT;
    
    public Servidor(int costo,boolean empty) {
        this.empty = empty;
        
        this.tiempoUtilizacion = 0;
        this.costo = costo;
        this.cantidadVecesUsado = 0;
        this.DT= 999999999;
        
    }
    


    


    

}
