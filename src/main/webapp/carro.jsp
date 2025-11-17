<%--
  Created by IntelliJ IDEA.
  User: ndadri
  Date: 13/11/2025
  Time: 8:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="models.*" %>
<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %>
<%--    Traemos la sesión Scriplets--%>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>

<html>
<head>
    <title>Carro de compras</title>
</head>

<body>
<div class="container mt-5">
    <h1 class="mb-4">Carro de Compras</h1>

    <%
        if(detalleCarro == null || detalleCarro.getItem().isEmpty() ) {%>
    <div class="alert alert-info">
        <p>Lo sentimos no hay productos en el carro de compras</p>
    </div>
    <% } else {%>

    <table class="table table-striped table-hover align-middle">
        <thead class="table-success">
        <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (ItemCarro item: detalleCarro.getItem()) {%>
        <tr>
            <td><%=item.getProducto().getIdProducto()%></td>
            <td><%=item.getProducto().getNombre()%></td>
            <td>$<%=item.getProducto().getPrecio()%></td>
            <td><%=item.getCantidad()%></td>
            <td>$<%=item.getSubtotal()%></td>
        </tr>
        <% }%>
        </tbody>
        <tfoot>
        <tr class="table-light">
            <td colspan="4" class="text-end fw-semibold">Subtotal:</td>
            <td class="fw-semibold">$<%=String.format("%.2f",detalleCarro.getSubtotal())%></td>
        </tr>
        <tr class="table-light">
            <td colspan="4" class="text-end fw-semibold">IVA (15%):</td>
            <td class="fw-semibold">$<%=String.format("%.2f", detalleCarro.getTotalIva())%></td>
        </tr>
        <tr class="table-success">
            <td colspan="4" class="text-end fw-bold">Total a Pagar:</td>
            <td class="fw-bold">$<%=String.format("%.2f", detalleCarro.getTotal())%></td>
        </tr>
        </tfoot>
    </table>
    <%}%>

    <div class="mt-4">
        <a href="<%=request.getContextPath()%>/carro/pdf" class="btn btn-success">
            <i class="bi bi-file-pdf"></i> Descargar Factura PDF
        </a>
        <a href="<%=request.getContextPath()%>/productos" class="btn btn-primary">
            <i class="bi bi-cart-plus"></i> Seguir Comprando
        </a>
        <a href="<%=request.getContextPath()%>/index.html" class="btn btn-secondary">
            <i class="bi bi-house"></i> Volver
        </a>
    </div>
</div>
</body>
</html>
