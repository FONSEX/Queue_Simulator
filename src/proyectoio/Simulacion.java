/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import proyectoio.Servidor;
import proyectoio.Tiempo;


/**
 *
 * @author juan_
 */
public class Simulacion {
    
     ArrayList<Servidor> servidores;                        //Aqui se guardan los n servidores de la simulacion
     ArrayList<ArrayList<Tiempo>> tiemposLlegadaInfo;       // Aqui se guardan las tablas de tiempos de llegada de todos los dias de la simulacion
     ArrayList<Tiempo> tiemposServicios;                    // Aqui se guarda la tabla de tiempos de servicio
     Queue<Cliente> colaClientes;                           // Aqui se guardan los clientes que estaran en cola
     Queue<Cliente> estadisticasCliente;  
     ArrayList<Integer> numeroClientesSistema;              // se suman todos los valores y se dividen entre el tiempo total de la simulacion para hallar L o cantidad promedio de clientes en el sistema 
     ArrayList<Integer> numeroClientesCola;
     
     int costoEspera;
     int tiempoSimulacion;                                 // posee la Unidad de tiempo que selecciono el usuario de min a años
     int tiempoTotalSimulacion;
     int unidadTotal;
     int tiempoModelo;
     int cantMaxClientes; 
     int AT;                                          // Tiempo programado para la siguiente llegada
     int cantEventos;
     int cantClientesNoEspera;
     int cantClientesNoAtendidos;
     int cantClienteCola;                 
     int tiempoPromedioClienteCola;
     int tiempoPromedioClienteSistema; 
     int cantidadTotalTiempoClientesServidor;  
     int cantidadTotalTiempoClientesCola;         
     int cantClientesSistema;
     int tablaEventos; 
     int generarArchivo;
     float costoEsperaCliente;
     float estadisticasCola;
     float estadisticasSistema;
     float tiempoModeloEstadisticas;
     float tiempoDespuesCerrar;
     
    
    

    
    public Simulacion(int tiempoSimulacion, int cantClientes, ArrayList<ArrayList<Tiempo>> tiemposLlegada, int costoServidor, int costoEspera,int cantidadServidores, int tiempoTotal,ArrayList<Servidor> servidores,ArrayList<Tiempo> tiemposServicios, int eventos, int archivo)
    {
        this.numeroClientesSistema= new ArrayList ();
        this.numeroClientesCola= new ArrayList ();
        this.tiempoModelo=0;
        this.cantEventos = 0;
        this.cantClientesNoEspera = 0;
        this.cantClientesNoAtendidos = 0;
        this.costoEsperaCliente = 0;
        this.cantidadTotalTiempoClientesServidor=0;
        this.cantClientesSistema=0;
        this.tiempoPromedioClienteCola=0;
        this.tiempoPromedioClienteSistema=0;
        this.cantClienteCola= 0;
        this.cantidadTotalTiempoClientesCola = 0;
        this.AT = 0;
        this.estadisticasCola=0;
        this.estadisticasSistema=0;
        this.tiempoDespuesCerrar=0;
        
        
        this.tablaEventos=eventos;
        this.generarArchivo=archivo;
        this.colaClientes = new LinkedList();
        this.estadisticasCliente= new LinkedList ();
        this.tiempoSimulacion = tiempoSimulacion;
        this.cantMaxClientes = cantClientes;
        this.tiemposLlegadaInfo = tiemposLlegada;
        this.tiemposServicios = tiemposServicios;
        this.costoEspera = costoEspera;
        this.servidores = servidores;
        this.tiempoTotalSimulacion = tiempoTotal;
        this.unidadTotal = tiempoTotal;
        
    }
    
    public void Inicio(){

        /* Aqui pasamos a convertir los la unidad de tiempo asignada por el cliente a minutos para conveniencia del algoritmo  */
        /*  Cada dia equivale a 7 Horas ya que el banco trabaja de 8am a 3pm, cada semana a 5 dias ya que se trabaja de lunes a viernes, cada mes son 4 semanas y cada año son 12 meses */
            
        switch ((int)this.tiempoSimulacion) {           
            case 0:
                this.tiempoTotalSimulacion = (int)this.tiempoTotalSimulacion;
                break; 
            case 1:  
                this.tiempoTotalSimulacion = 60 * (int)this.tiempoTotalSimulacion;
                break;
            case 2: 
                this.tiempoTotalSimulacion = 420* (int)this.tiempoTotalSimulacion;   
                break;
            case 3: 
                this.tiempoTotalSimulacion = 5 * (int)this.tiempoTotalSimulacion * 420;
                break;
            case 4: 
                this.tiempoTotalSimulacion = 20 * (int)this.tiempoTotalSimulacion * 420;
                break;
            case 5: 
                this.tiempoTotalSimulacion = 240 * (int)this.tiempoTotalSimulacion * 420;       
                break;
        }
        
        /*EMPIEZA LA SIMULACION*/
        
        do {     // se hace mientras que el tiempo de Modelo sea menor que tiempo total de la simulacion
            
            if (AT<servidores.get(menorDT(servidores)).DT) {  // Procesando una entrada !!

                tiempoModelo=AT;

                    if (servidoresVacios(servidores)!=-1) {   // si hay un servidor disponible agrego un cliente
                       
                        servidores.get(servidoresVacios(servidores)).cliente= new Cliente (0,0,costoEspera);
                        servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio=generarTiempo(tiemposServicios);
                        cantidadTotalTiempoClientesServidor=cantidadTotalTiempoClientesServidor+servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio;
                        servidores.get(servidoresVacios(servidores)).tiempoUtilizacion=servidores.get(servidoresVacios(servidores)).tiempoUtilizacion+servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio;
                        servidores.get(servidoresVacios(servidores)).DT=servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio+tiempoModelo;
                        servidores.get(servidoresVacios(servidores)).empty=false; 
                        this.cantClientesNoEspera++;

                } else {
                        
                       if(colaClientes.size()<cantMaxClientes) {
                
                           colaClientes.add(new Cliente (tiempoModelo,0,costoEspera));
                           cantClienteCola++;

                } else {
                       
                       cantClientesNoAtendidos++;
                       
                       }

                }

                switch ((int) comprobarDia(this.tiempoModelo)) {   // Cada caso equivale un dia y cada dia tiene una tabla distinta de probabilidades y tiempos de llegada
                                                                    
                case 0:
                        AT=tiempoModelo+generarTiempo( tiemposLlegadaInfo.get(0));
                        
                    break;
            
                case 1:
                        AT=tiempoModelo+generarTiempo( tiemposLlegadaInfo.get(1));
                    break;
            
                case 2:
                        AT=tiempoModelo+generarTiempo( tiemposLlegadaInfo.get(2));
                    break;
            
                case 3:
                        AT=tiempoModelo+generarTiempo( tiemposLlegadaInfo.get(3));
                    break;
            
                case 4:
                        AT=tiempoModelo+generarTiempo( tiemposLlegadaInfo.get(4));
                    break;
            
            }
                
                /* Inicio agregando al arreglo que servira para hacer estadisticas, la cantidad de clientes en este evento */
                
                if (servidoresVacios(servidores)!=-1)
                    numeroClientesSistema.add(colaClientes.size()+servidoresVacios(servidores));
                else 
                    numeroClientesSistema.add(colaClientes.size()+servidores.size());  
                    numeroClientesCola.add(colaClientes.size());
                
                /* Fin agregando al arreglo que servira para hacer estadisticas, la cantidad de clientes en este evento */
                
                /* Inicio Mostrando evento Llegada */
                
                if (tablaEventos==1) {
                    
                    System.out.print("Evento Nro:"+cantEventos+" || Tipo de evento: Llegada || TiempoModelo:"+tiempoModelo+" || Servidores usados:"+servidoresVacios(servidores)+" || Longitud de la cola:"+colaClientes.size()+" || AT:"+AT+" || ");
                    for (int i=0; i<servidores.size();i++)
                        System.out.print("DT"+i+": "+String.valueOf(servidores.get(i).DT)+" || ");
                    System.out.println(" ");
                
                
                }
                
                /* Fin Mostrando evento Llegada */
                
            } else {        // Procesando una salida !!

                tiempoModelo=servidores.get(menorDT(servidores)).DT;

                if(colaClientes.size()>0) {
                    
                  
                        
                         for(int i=0;i<servidores.size();i++) {

                            if (servidores.get(i).empty==false && colaClientes.size()>0) {

                                 if (servidores.get(i).DT<=tiempoModelo) {
                                        estadisticasCliente.add(servidores.get(i).cliente);
                                        servidores.get(i).cantidadVecesUsado++;
                                        servidores.get(i).empty=true;

                                        servidores.get(i).cliente= colaClientes.poll();
                                        servidores.get(i).cliente.tiempoEnCola=tiempoModelo-servidores.get(i).cliente.tiempoLlegadaCola;
                                        cantidadTotalTiempoClientesCola=cantidadTotalTiempoClientesCola+(tiempoModelo-servidores.get(i).cliente.tiempoLlegadaCola);
                                        servidores.get(i).cliente.tiempoServicio=generarTiempo(tiemposServicios);
                                        cantidadTotalTiempoClientesServidor=cantidadTotalTiempoClientesServidor+servidores.get(i).cliente.tiempoServicio;
                                        servidores.get(i).tiempoUtilizacion=servidores.get(servidoresVacios(servidores)).tiempoUtilizacion+servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio;
                                        
                                        servidores.get(i).DT=servidores.get(i).cliente.tiempoServicio+tiempoModelo;
                                        servidores.get(i).empty=false;

                                  }      
                                           
                            } 

                        }
      

                } else {

                       for(int i=0;i<servidores.size();i++) {

                    if (servidores.get(i).empty==false) {
                            estadisticasCliente.add(servidores.get(i).cliente);
                            servidores.get(i).cantidadVecesUsado++;
                            servidores.get(i).empty=true;

                    }

                }
                    for (int i=0; i<servidores.size();i++)
                    servidores.get(i).DT=999999999;
            }
                
            /* Inicio agregando al arreglo que servira para hacer estadisticas, la cantidad de clientes en este evento */
                
                if (servidoresVacios(servidores)!=-1)
                numeroClientesSistema.add(colaClientes.size()+servidoresVacios(servidores));
                else 
                numeroClientesSistema.add(colaClientes.size()+servidores.size()); 
                numeroClientesCola.add(colaClientes.size());
                
                
            /* Fin agregando al arreglo que servira para hacer estadisticas, la cantidad de clientes en este evento */
                
             /* Inicio Mostrando evento Salida */ 
             
             if (tablaEventos==1) {
                 
                 System.out.print("Evento Nro:"+cantEventos+" || Tipo de evento: Salida  || TiempoModelo:"+tiempoModelo+" || Servidores usados:"+servidoresVacios(servidores)+" || Longitud de la cola:"+colaClientes.size()+" || AT:"+AT+" || ");
                 for (int i=0; i<servidores.size();i++)
                    System.out.print("DT"+i+": "+String.valueOf(servidores.get(i).DT)+" || "); 
                 System.out.println(" ");
  
             }
             
            /* Fin Mostrando evento Salida */     

        }
 
            cantEventos++;

        }  while (tiempoTotalSimulacion>tiempoModelo);  
        
        /* Si aun quedan clientes en la cola, seran atendidos */
        
        if (colaClientes.size()>0) {

            while (colaClientes.size()>0) {

                         for(int i=0;i<servidores.size();i++) {

                            if (servidores.get(i).empty==false && colaClientes.size()>0) {
                                        estadisticasCliente.add(servidores.get(i).cliente);
                                        servidores.get(i).cantidadVecesUsado++;
                                        servidores.get(i).empty=true;
                                        servidores.get(i).cliente=null;
                                        
                                        servidores.get(servidoresVacios(servidores)).cliente= colaClientes.poll();
                                        servidores.get(i).cliente.tiempoEnCola=tiempoModelo-servidores.get(i).cliente.tiempoLlegadaCola;
                                        servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio=generarTiempo(tiemposServicios);
                                        cantidadTotalTiempoClientesServidor=cantidadTotalTiempoClientesServidor+servidores.get(i).cliente.tiempoServicio;
                                        servidores.get(servidoresVacios(servidores)).tiempoUtilizacion=servidores.get(servidoresVacios(servidores)).tiempoUtilizacion+servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio;
                                        
                                        tiempoModelo=servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio+tiempoModelo;
                                        servidores.get(servidoresVacios(servidores)).empty=false;
                                    
                        
                            }else if(colaClientes.size()>0){
                            
                                        servidores.get(i).cliente= colaClientes.poll();
                                        servidores.get(i).cliente.tiempoEnCola=tiempoModelo-servidores.get(i).cliente.tiempoLlegadaCola;
                                        cantidadTotalTiempoClientesCola=cantidadTotalTiempoClientesCola+(tiempoModelo-servidores.get(i).cliente.tiempoLlegadaCola);
                                        servidores.get(i).cliente.tiempoServicio=generarTiempo(tiemposServicios);
                                        cantidadTotalTiempoClientesServidor=cantidadTotalTiempoClientesServidor+servidores.get(i).cliente.tiempoServicio;
                                        servidores.get(i).tiempoUtilizacion=servidores.get(servidoresVacios(servidores)).tiempoUtilizacion+servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio;
                                        
                                        tiempoModelo=servidores.get(servidoresVacios(servidores)).cliente.tiempoServicio+tiempoModelo;
                                        servidores.get(i).empty=false;
                            
                            }

                        }

            }

        }
        
         /* Si aun quedan clientes en los servidores, terminaran de ser procesados*/
        
        for(int i=0;i<servidores.size();i++) {
            
            if (servidores.get(i).empty==false) {
                estadisticasCliente.add(servidores.get(i).cliente);
                servidores.get(i).cantidadVecesUsado++;
                servidores.get(i).empty=true;
                servidores.get(i).cliente=null;

            }
        }
        
        /* Inicio Convirtiendo parametros a sus unidades de tiempo establecidas */
        
        estadisticasCola=calcularWQ(estadisticasCliente);
        estadisticasSistema=calcularW(estadisticasCliente);
        costoEsperaCliente= (calcularWQ (estadisticasCliente)*costoEspera);
        tiempoDespuesCerrar=tiempoModelo-tiempoTotalSimulacion;
        tiempoModeloEstadisticas=tiempoModelo;
        
        
        if (tiempoSimulacion==1) {
            
            tiempoModeloEstadisticas=tiempoModeloEstadisticas/60;
            estadisticasCola=estadisticasCola/60;
            estadisticasSistema=estadisticasSistema/60;
            
            tiempoDespuesCerrar=tiempoDespuesCerrar/60;
            
            
        
        } else if (tiempoSimulacion==2) {
            
            tiempoModeloEstadisticas=tiempoModeloEstadisticas/420;
            estadisticasCola=estadisticasCola/420;
            estadisticasSistema=estadisticasSistema/420;
            
            tiempoDespuesCerrar=tiempoDespuesCerrar/420;
            
        
        } else if (tiempoSimulacion==3) {
            
            tiempoModeloEstadisticas=tiempoModeloEstadisticas/2100;
            estadisticasCola=estadisticasCola/2100;
            estadisticasSistema=estadisticasSistema/2100;
            
            tiempoDespuesCerrar=tiempoDespuesCerrar/2100;
        
        } else if (tiempoSimulacion==4) {
            
            tiempoModeloEstadisticas=tiempoModeloEstadisticas/8400;
            estadisticasCola=estadisticasCola/8400;
            estadisticasSistema=estadisticasSistema/8400;
            
            tiempoDespuesCerrar=tiempoDespuesCerrar/8400;
        
        } else if (tiempoSimulacion==5) {
            
            tiempoModeloEstadisticas=tiempoModeloEstadisticas/100800;
            estadisticasCola=estadisticasCola/100800;
            estadisticasSistema=estadisticasSistema/100800;
            
            tiempoDespuesCerrar=tiempoDespuesCerrar/100800;
        
        }
        
         /* Fin Convirtiendo parametros a sus unidades de tiempo establecidas */
        
        /*TERMINA LA SIMULACION*/
        
        /* MOSTRANDO ESTADISTICAS */
        
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

        for(int i=0;i<servidores.size();i++) { /* % de utilizacion de cada servidor */

                            System.out.println("Servidor "+i+" fue usado:"+ servidores.get(i).cantidadVecesUsado+" Veces");
                            System.out.println("Porcentaje de utilizacion:"+ (float)(servidores.get(i).cantidadVecesUsado)/(clientesAtendidos(servidores))*100+"%");
                            System.out.println("Tiempo del uso del Servidor "+i+"(tiempoEnMin):"+ servidores.get(i).tiempoUtilizacion); 
                            System.out.println("Costo servidor: "+((((float)servidores.get(i).tiempoUtilizacion/(float)servidores.get(i).cantidadVecesUsado*servidores.get(i).costo)))+ "USD");

                }
        
        System.out.println("Total de clientes en el Sistema: "+ estadisticasCliente.size());

        Estadisticas nueva;  // Creando el Jframe para mostrar ciertas estadisticas
        
        /* Valores a enviar: Clientes que no esperan || Clientes que no son atendidos || Probabilidad de esperar || Cantidad promedio de clientes en el sistema ||
           Tiempo adicional que se trabaja despues de cerrar || Tiempo promedio de espera de cliente que hace cola || Tiempo promedio de un cliente en el sistema ||
           costo promedio de espera del cliente || tiempo total de la simulacion */
        
        nueva = new Estadisticas(cantClientesNoEspera,cantClientesNoAtendidos,probEsperar(cantClienteCola,cantClientesNoEspera),calcularL (numeroClientesSistema,tiempoTotalSimulacion),
        tiempoDespuesCerrar, estadisticasCola,estadisticasSistema,costoEsperaCliente,tiempoModeloEstadisticas,calcularLQ(numeroClientesCola,tiempoTotalSimulacion));
        
        
        
        if (generarArchivo==1) // Si se solicito generar un archivo con las estadisticas se generara
        generarArchivo();
        
        
        nueva.setVisible(true);

    }
   
    
    public float probEsperar(int clientesEsperan, int clientesNoEsperan) {
        
        float totalClientes = clientesEsperan+clientesNoEsperan;
        
        return (clientesEsperan/totalClientes*100);
    
    
    
    }
    
    public float calcularWQ ( Queue<Cliente> C) {
        
      float suma = 0;  
      float N = C.size();
     
      for (Cliente variable : C) {

      suma=suma+variable.tiempoEnCola;

      }

      return suma/N;

    }
    
    
    public float calcularW ( Queue<Cliente> Clientes ) {
        
      float suma = 0;  
      float N = Clientes.size();

      for (Cliente variable : Clientes) {
      
      suma=suma+variable.tiempoEnCola+variable.tiempoServicio; 

      }
 
      return suma/N;

    }
    
    public float calcularLQ ( ArrayList<Integer> A, int tiempoTotalSimulacion) {
        
        float suma = 0 ;
        float LQ = 0;
        
        for (int i=0; i<A.size(); i++) {
        
            suma=suma + A.get(i);

        }
        
        LQ= suma/tiempoTotalSimulacion; 
        
        return LQ;

    }
    
    
    
    
    public float calcularL ( ArrayList<Integer> A, int tiempoTotalSimulacion) {
        
        float suma = 0 ;
        float L = 0;
        
        for (int i=0; i<A.size(); i++) {
        
            suma=suma + A.get(i);

        }
        
        L= suma/tiempoTotalSimulacion; 
        
        return L;

    }
    
    /* Esta funcion genera un numero al azar del 0 al 99 y busca en la tabla que se le ingreso la seccion a la que corresponde dicho numero */
    
     public int generarTiempo(ArrayList<Tiempo> tabla){ 
 
        Random rand = new Random();
        int valorAleatorio = rand.nextInt(99);
        float probAcumulada=0;
        
        for(int i=0;i<tabla.size();i++) {            // ubicando la posicion del numero aleatorio
            
            probAcumulada= probAcumulada+tabla.get(i).getProbabilidad();
            
            if (valorAleatorio<=Math.round(probAcumulada*100))
            return tabla.get(i).getValor();            
        }

        return -1;
    }
     
     /* Comprueba que dia de la semana es en la simulacion*/
    
    public int comprobarDia (int totalSimulacion) {
        
        if ((totalSimulacion/100800)>=1) {
            
          return comprobarDia (totalSimulacion-100800);
            
        } else 
        if ((totalSimulacion/8400)>=1) {
            
          return comprobarDia (totalSimulacion-8400);
        
        } else 
        if ((totalSimulacion/2100)>=1) {
         
          return comprobarDia (totalSimulacion-2100);
        
        }else {
            
            if (totalSimulacion<420) {
                return 0; 
            } else 
            
            if (totalSimulacion<840 && totalSimulacion>=420 ) {
                return 1;
                
            } else
            if (totalSimulacion<1260 && totalSimulacion>=840 ) {
                return 2;
            
            } else
            if (totalSimulacion<1680 && totalSimulacion>=1260) {
                return 3;
                
            
            } else
            if (totalSimulacion<2100 && totalSimulacion>=1260) {
                return 4;
            
            }

        }
       
        return 0;
    }
    
     /* devuelve el primer servidor vacio o -1 si no hay ningun servidor vacio*/
    
    public int servidoresVacios (ArrayList<Servidor> servidores) {     
 
        for(int i=0;i<servidores.size();i++) { 
            
            if (servidores.get(i).empty == true) 
                return i; 
        }

   return -1;
   }
    
    /* Promedia la cantidad de veces que fueron utilizados los servidores*/
    
    public int promedioUsoServidores (ArrayList<Servidor> servidores) {
    
        int promedio=0;
        for(int i=0;i<servidores.size();i++) { 
            
            promedio=promedio+servidores.get(i).cantidadVecesUsado;
        }
        
        return promedio/servidores.size();
    
    
    }
    
    /*Promedia la cantidad de clientes atendidos en un servidor*/
    
    public int clientesAtendidos(ArrayList<Servidor> servidores) {
    
        int clientesA=0;
        for(int i=0;i<servidores.size();i++) { 
            
            clientesA=clientesA+servidores.get(i).cantidadVecesUsado;
        }
        
        return clientesA;
    
    
    }

    public int unidadTiempo (int valor) {

        if (valor==0) {

            return 1;
        
        } else if (valor==1) {

            return 60;

        }else if (valor==2) {

            return 420;

        }else if (valor==3) {
            return 2100;

        }else if (valor==4) {

            return 8400;

        }else if (valor==5) {
            return 100800;
        }

            return 1;
        
        }
    
 

 public int mayorDT () {
 
 return 1;
 }
 
 public int menorDT ( ArrayList<Servidor> servidores) {
     
    int menorDT=999999999;
    int ubicacion=0;
    
    for(int i=0;i<servidores.size();i++) { 
        
        if (servidores.get(i).DT<menorDT) {
            menorDT=servidores.get(i).DT;
            ubicacion=i;
       
        }
   
    }

    return ubicacion;
 }
 
 public void generarArchivo(){
     
     Calendar calendario = Calendar.getInstance();
        try {
            String ruta = "./Archivos/SimulacionColas_"+calendario.get(Calendar.HOUR)+"_" +calendario.get(Calendar.MINUTE)+"_"+calendario.get(Calendar.SECOND)+".txt" ;
            String noEsperan = "Cantidad de Clientes que no esperan: " + String.valueOf(this.cantClientesNoEspera);
            String sinSerAtendidos = "Cantidad de Clientes que se van sin ser atendidos: " + String.valueOf(this.cantClientesNoAtendidos);
            String esperan = "Probabilidad de esperar: " + String.valueOf(probEsperar(this.cantClienteCola,this.cantClientesNoEspera))+ "%";
            String L = "Cantidad promedio de clientes en el sistema: "+ String.valueOf( calcularL (this.numeroClientesSistema,this.tiempoTotalSimulacion));
            String LQ = "Cantidad promedio de clientes en cola: "+String.valueOf(calcularLQ(this.numeroClientesCola,this.tiempoTotalSimulacion));
            String W = "Tiempo promedio de un cliente en el sistema: " + String.valueOf( this.estadisticasSistema);
            String WQ = "Tiempo promedio de un cliente en cola: "+ String.valueOf(this.estadisticasCola);
            String tiempoAdicional = "Tiempo adicional que se trabaja despues de cerrar: "+ (this.tiempoDespuesCerrar);
            String tiempoModelo = "Tiempo del Modelo: "+ this.tiempoModelo;
            String costoEspera = "Costo promedio de espera: "+this.costoEsperaCliente+ " USD";
            
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(noEsperan);
            bw.newLine();
            bw.write(sinSerAtendidos);
            bw.newLine();
            bw.write(esperan);
            bw.newLine();
            bw.write(L);
            bw.newLine();
            bw.write(LQ);
            bw.newLine();
            bw.write(W);
            bw.newLine();
            bw.write(WQ);
            bw.newLine();
            bw.write(tiempoAdicional);
            bw.newLine();
            bw.write(tiempoModelo);
            bw.newLine();
            bw.write(costoEspera);
            bw.newLine();
            bw.write("__________________________________________________________________________________________________");
            bw.newLine();
            
             for(int i=0;i<this.servidores.size();i++) { /* % de utilizacion de cada servidor */
                bw.newLine();
                bw.write("Servidor "+i+" fue usado:"+ this.servidores.get(i).cantidadVecesUsado+" Veces");
                bw.newLine();
                bw.write("Porcentaje de utilizacion:"+ (float)(this.servidores.get(i).cantidadVecesUsado)/(clientesAtendidos(servidores))*100+"%");
                bw.newLine();
                bw.write("Tiempo del uso del Servidor "+i+"(tiempoEnMin):"+ this.servidores.get(i).tiempoUtilizacion);  
                bw.newLine();
                bw.write("Costo servidor: "+(((float)this.servidores.get(i).tiempoUtilizacion/(float)this.servidores.get(i).cantidadVecesUsado*this.servidores.get(i).costo))+ "USD");

                }
            
            
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 

 
}

        


