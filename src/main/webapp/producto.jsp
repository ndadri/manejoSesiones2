<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 21/11/2025
  Time: 8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*, models.*"%>

<%
    // Aquí recupero desde el request la lista de productos que el servlet envió.
    // Esta lista es la que voy a mostrar en la tabla.
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");

    // También recupero el username, pero viene como Optional.
    // Esto me sirve para saber si el usuario inició sesión o no.
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>

<html>
<head>
    <title>Listado de Productos</title>
</head>

<body>

<div>

    <h1>Listado de Productos</h1>

    <%
        // Si el usuario está logueado, muestro un saludo y el botón para crear productos.
        // Esto asegura que solo usuarios registrados puedan gestionar productos.
        if (username.isPresent()) {
    %>
    <div>
        Hola <strong><%=username.get()%></strong>, ¡Bienvenido!
    </div>

    <div>
        <!-- Enlace para ir al formulario de creación de productos -->
        <a href="<%=request.getContextPath()%>/productos/form">Crear un producto</a>
    </div>
    <% } %>

    <table border="1" cellspacing="0" cellpadding="5">

        <thead>
        <tr>
            <!-- Encabezados de la tabla, uno por cada atributo del producto -->
            <th>Id Producto</th>
            <th>Nombre del Producto</th>
            <th>Categoria</th>
            <th>Stock</th>
            <th>Descripción</th>
            <th>Fecha Elaboración</th>
            <th>Fecha Caducidad</th>
            <th>Condición</th>

            <%
                // Solo muestro precio y las acciones (botones) si hay usuario logueado.
                if (username.isPresent()) {
            %>
            <th>Precio</th>
            <th>Acción</th>
            <% } %>
        </tr>
        </thead>

        <tbody>
        <%
            // Recorro la lista de productos y los voy pintando en la tabla.
            for (Producto p : productos) {
        %>
        <tr>
            <!-- Aquí imprimo cada atributo del producto -->
            <td><%=p.getId()%></td>
            <td><%=p.getNombreProducto()%></td>
            <td><%=p.getCategoria().getNombre()%></td>
            <td><%=p.getCantidad()%></td>
            <td><%=p.getDescripcion()%></td>
            <td><%=p.getFechaElaboracion()%></td>
            <td><%=p.getFechaCaducidad()%></td>

            <!-- La condición del producto (por ejemplo 1 activo, 0 inactivo) -->
            <td><%=p.getCondicion()%></td>

            <% if (username.isPresent()) { %>
            <!-- Muestro el precio solo para usuarios con sesión -->
            <td>$<%=p.getPrecio()%></td>

            <td>
                <%
                    // Si el producto está activo (condición = 1), muestro los botones
                    // para agregar al carro y para desactivar el producto.
                    if (p.getCondicion() == 1) {
                %>
                <!-- Agregar al carro -->
                <a href="<%=request.getContextPath()%>/agregar-carro?id=<%=p.getId()%>">Agregar al carro</a>

                <!-- Desactivar producto -->
                <a href="<%=request.getContextPath()%>/eliminar?id=<%=p.getId()%>"
                   onclick="return confirm('¿Seguro que desea desactivar?');">
                    Desactivar
                </a>

                <%
                    // Si el producto está inactivo (condición diferente de 1)
                    // muestro la opción para activarlo nuevamente.
                } else {
                %>
                <a href="<%=request.getContextPath()%>/activar?id=<%=p.getId()%>">
                    Activar
                </a>
                <% } %>
            </td>
            <% } %>
        </tr>
        <% } %>
        </tbody>
    </table>

    <!-- Botón para volver al inicio -->
    <div>
        <a href="<%=request.getContextPath()%>/index.html">Volver</a>
    </div>

</div>

</body>
</html>
