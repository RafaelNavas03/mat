package com.example.Matemagicas.controlador;

import com.example.Matemagicas.modelos.Calificacion;
import com.example.Matemagicas.modelos.Estudiante;
import com.example.Matemagicas.modelos.Resultado;
import com.example.Matemagicas.repositorio.CalificacionRepository;
import com.example.Matemagicas.repositorio.EstudianteRepository;
import com.example.Matemagicas.repositorio.ResultadoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para enviar puntuación y calcular si es satisfactoria.
 */
@Controller
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ResultadoRepository resultadoRepository; // Inyecta el repositorio de Resultado

    @GetMapping("/enviarpuntuacion")
    public String enviarPuntuacion(
            @RequestParam("materia") String materia,
            @RequestParam("calificacion") Double calificacion,
            HttpSession session
    ) {
        // Obtén el estudianteId desde la sesión
        Long estudianteId = (Long) session.getAttribute("estudianteId");

        if (estudianteId != null) {
            // Obtén el estudiante correspondiente a partir del estudianteId
            Estudiante estudiante = estudianteRepository.findById(estudianteId).orElse(null);

            if (estudiante != null) {
                // Crea una nueva instancia de Calificacion
                Calificacion nuevaCalificacion = new Calificacion();
                nuevaCalificacion.setMateria(materia);
                nuevaCalificacion.setCalificacion(calificacion);

                // Asigna el estudiante a la calificación
                nuevaCalificacion.setEstudiante(estudiante);

                // Guarda la calificación en la base de datos
                calificacionRepository.save(nuevaCalificacion);

                // Calcula si la calificación es satisfactoria (ajusta estos criterios según tus necesidades)
                boolean esSatisfactorio = calificacion >= 7.0; // Por ejemplo, considera que una calificación mayor o igual a 5 es satisfactoria

                // Obtén la entidad Resultado asociada a la calificación
                Resultado resultado = nuevaCalificacion.getResultado();

                if (resultado == null) {
                    resultado = new Resultado();
                }

                // Actualiza el valor de esSatisfactorio en la entidad Resultado
                resultado.setEsSatisfactorio(esSatisfactorio);

                // Asocia la entidad Resultado con la Calificacion
                resultado.setCalificacion(nuevaCalificacion);

                // Guarda la Calificacion y el Resultado actualizados
                calificacionRepository.save(nuevaCalificacion);
                resultadoRepository.save(resultado); // Utiliza resultadoRepository, no ResultadoRepository

                // Puedes redirigir al usuario a una página de confirmación o a donde desees
                return "redirect:/actividades";
            }
        }
        return "redirect:/"; // Redirige a la página de inicio o maneja el error de alguna otra manera
    }
}