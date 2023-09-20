package com.example.Matemagicas.repositorio;

import com.example.Matemagicas.modelos.Resultado;
import java.util.List; // Importa java.util.List en lugar de java.awt.List
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    // Método personalizado para buscar resultados por estado de satisfacción
    List<Resultado> findByEsSatisfactorio(boolean esSatisfactorio);
}


