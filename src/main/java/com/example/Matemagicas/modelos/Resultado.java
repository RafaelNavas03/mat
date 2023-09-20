package com.example.Matemagicas.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Resultado")
@Data
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean esSatisfactorio;

    @OneToOne
    @JoinColumn(name = "calificacion_id") // Esto establece la clave foránea en la tabla Resultado
    private Calificacion calificacion;

    // Agrega los métodos setters y getters aquí
    public boolean isEsSatisfactorio() {
        return esSatisfactorio;
    }

    public void setEsSatisfactorio(boolean esSatisfactorio) {
        this.esSatisfactorio = esSatisfactorio;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }
}
