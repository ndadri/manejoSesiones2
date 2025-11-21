package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.DetalleCarro;
import models.ItemCarro;

import java.io.IOException;

@WebServlet("/carro/pdf")
public class GenerarPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // obtener la sesión actual del usuario y recuperar el carrito almacenado
        HttpSession session = req.getSession();
        DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");

        // validar que el carrito exista y tenga productos.
        // si esta vacio, redirige nuevamente a la vista del carrito.
        if (detalleCarro == null || detalleCarro.getItem().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/carro");
            return;
        }

        // configurar la respuesta como para descargar como pdf
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=factura_carrito.pdf");

        try {
            // Crear el documento pdf
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            document.open();

            // agregar titulo a la factura
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("FACTURA - CARRITO DE COMPRAS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Espacio

            // informacion general del carrito
            document.add(new Paragraph("Total de productos: " + detalleCarro.getItem().size()));
            document.add(new Paragraph(" "));

            // crear tabla principal con 5 columnas (ID, Producto, Precio, Cantidad, Subtotal)
            PdfPTable table = new PdfPTable(5); // 5 columnas
            table.setWidthPercentage(100);// ocupa todo el ancho disponible
            table.setWidths(new float[]{1, 3, 2, 1.5f, 2});// configuracion de tamaño de columnas

            // crear encabezados de la tabla en negrita
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            table.addCell(new Phrase("ID", headerFont));
            table.addCell(new Phrase("Producto", headerFont));
            table.addCell(new Phrase("Precio", headerFont));
            table.addCell(new Phrase("Cantidad", headerFont));
            table.addCell(new Phrase("Subtotal", headerFont));

            // recorrer los items del carrito y agregarlos como filas en la tabla
            for (ItemCarro item : detalleCarro.getItem()) {
                table.addCell(String.valueOf(item.getProducto().getId()));
                table.addCell(item.getProducto().getNombreProducto());
                table.addCell(String.format("$%.2f", item.getProducto().getPrecio()));
                table.addCell(String.valueOf(item.getCantidad()));
                table.addCell(String.format("$%.2f", item.getSubtotal()));
            }

            // agregar la tabla al documento.
            document.add(table);
            document.add(new Paragraph(" "));

            // seccion de totales con alineacion a la derecha
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph subtotal = new Paragraph(
                    "Subtotal: $" + String.format("%.2f", detalleCarro.getSubtotal())
            );
            subtotal.setAlignment(Element.ALIGN_RIGHT);
            document.add(subtotal);

            Paragraph iva = new Paragraph(
                    "IVA (15%): $" + String.format("%.2f", detalleCarro.getTotalIva())
            );
            iva.setAlignment(Element.ALIGN_RIGHT);
            document.add(iva);

            Paragraph total = new Paragraph(
                    "TOTAL A PAGAR: $" + String.format("%.2f", detalleCarro.getTotal()),
                    boldFont
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.close();

        } catch (DocumentException e) {
            // manejar errores de creacion del documento pdf
            throw new IOException("Error al generar el PDF", e);
        }
    }
}
