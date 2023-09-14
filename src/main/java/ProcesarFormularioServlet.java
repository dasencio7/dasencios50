import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/procesar")
public class ProcesarFormularioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String fechaNacimientoStr = request.getParameter("fechaNacimiento");

        try {
            // Convertir la fecha de nacimiento en un objeto Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNacimiento = sdf.parse(fechaNacimientoStr);

            // Calcular la edad
            int edad = calcularEdad(fechaNacimiento);

            // Construir un mensaje personalizado
            String mensaje = "¡Hola, " + nombre + "! Tienes " + edad + " años.";

            // Configurar la respuesta HTTP
            response.setContentType("text/html;charset=UTF-8");

            // Enviar el mensaje como respuesta
            response.getWriter().println("<h2>" + mensaje + "</h2>");
        } catch (ParseException e) {
            // Manejo de error si la fecha de nacimiento no es válida
            response.getWriter().println("<p>La fecha de nacimiento ingresada no es válida.</p>");
        }
    }

    private int calcularEdad(Date fechaNacimiento) {
        // Calcular la edad a partir de la fecha de nacimiento
        Date fechaActual = new Date();
        int edad = fechaActual.getYear() - fechaNacimiento.getYear();

        // Verificar si el cumpleaños ya ocurrió este año
        if (fechaNacimiento.getMonth() > fechaActual.getMonth()
                || (fechaNacimiento.getMonth() == fechaActual.getMonth() && fechaNacimiento.getDate() > fechaActual.getDate())) {
            edad--;
        }

        return edad;
    }
}
