<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 21/11/2025
  Time: 8:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Producto" %>
<%@ page import="models.Categoria" %>
<%@ page import="java.util.List" %>
<%
    Producto producto = (Producto) request.getAttribute("producto");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    String titulo = (producto.getId() != null && producto.getId() > 0) ? "Editar Producto" : "Crear Producto";
%>
<html>
<head>
    <title><%= titulo %></title>
</head>
<body>

<h1><%= titulo %></h1>

<form action="<%= request.getContextPath() %>/productos/form" method="post">
    <input type="hidden" name="id" value="<%= (producto.getId() != null) ? producto.getId() : "" %>">

    <div>
        <label>Nombre del Producto:</label>
        <input type="text" name="nombre"
               value="<%= (producto.getNombreProducto() != null) ? producto.getNombreProducto() : "" %>"
               placeholder="Ej: Galletas de Chocolate" required>
    </div>

    <div>
        <label>Precio ($):</label>
        <input type="number" step="0.01" name="precio"
               value="<%= producto.getPrecio() > 0 ? producto.getPrecio() : "" %>"
               placeholder="0.00" required>
    </div>

    <div>
        <label>Stock (Unidades):</label>
        <input type="number" name="stock"
               value="<%= producto.getCantidad() %>" required>
    </div>

    <div>
        <label>Categoría:</label>
        <select name="categoria" required>
            <option value="">-- Seleccionar Categoría --</option>
            <% for(Categoria c : categorias) { %>
            <option value="<%= c.getId() %>"
                    <%= (producto.getCategoria() != null && c.getId().equals(producto.getCategoria().getId())) ? "selected" : "" %> >
                <%= c.getNombre() %>
            </option>
            <% } %>
        </select>
    </div>

    <div>
        <label>Descripción:</label>
        <textarea name="descripcion" rows="2" placeholder="Detalles del producto..."><%= (producto.getDescripcion() != null) ? producto.getDescripcion() : "" %></textarea>
    </div>

    <div>
        <label>Fecha Elaboración:</label>
        <input type="date" name="fecha_elaboracion"
               value="<%= producto.getFechaElaboracion() %>" required>
    </div>

    <div>
        <label>Fecha Caducidad:</label>
        <input type="date" name="fecha_caducidad"
               value="<%= producto.getFechaCaducidad() %>" required>
    </div>

    <div>
        <button type="submit">
            Guardar
        </button>
        <a href="<%= request.getContextPath() %>/productos">
            Cancelar
        </a>
    </div>
</form>

</body>
</html>
