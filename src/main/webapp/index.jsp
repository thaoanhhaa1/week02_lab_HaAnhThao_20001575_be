<%@ page import="java.util.List" %>
<%@ page import="vn.edu.iuh.fit.backend.models.ProductPrice" %>
<%@ page import="vn.edu.iuh.fit.backend.models.ProductImage" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Product" %>
<%@ page import="vn.edu.iuh.fit.frontend.utils.Utils" %>
<%@ page import="vn.edu.iuh.fit.backend.models.Customer" %>

<%
    Object products = request.getAttribute("products");
    String page_o = request.getParameter("page");

    if (page_o == null)
        page_o = "1";

    if (products == null) {
        request.getRequestDispatcher("control-servlet?action=products&page=" + page_o).forward(request, response);
        return;
    }

    session.removeAttribute("products");
    List<ProductPrice> productPrices = (List<ProductPrice>) products;
    long pages = (long) session.getAttribute("pages");
    long currentPage = Utils.convertToLong(page_o);
    Object customerO = session.getAttribute("customer");
%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Sales Website</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
    <main class="d-flex gap-4 flex-column">
        <jsp:include page="components/header.jsp" />
        <section>
            <div class="container">
                <%-- Products--%>
                <%
                    if (!productPrices.isEmpty()) {
                %>
                <div class="row row-gap-3">
                    <%
                        for (ProductPrice productPrice : productPrices) {
                            Product product = productPrice.getProduct();
                            List<ProductImage> productImageList = productPrice.getProduct().getProductImageList();
                            String imagePath = productImageList == null || productImageList.isEmpty() ? "images/alternate_image.png" : productImageList.get(0).getPath();
                    %>
                    <div class="col col-12 col-sm-6 col-md-4 col-lg-3 col-xl-3">
                        <div class="card h-full">
                            <div class="ratio ratio-4x3">
                                <img src="<%= imagePath %>" class="card-img-top object-fit-cover" alt="<%= product.getName() %>">
                            </div>
                            <div class="card-body">
                                <h5 class="card-title line-clamp-1">
                                    <a class="link-underline-light" href="product.jsp?id=<%= product.getProduct_id() %>"><%= product.getName() %></a>
                                </h5>
                                <p class="card-text line-clamp-3"><%= product.getDescription() %></p>
                                <form action="control-servlet?action=add-cart-detail" method="post" class="m-0">
                                    <%
                                        if (customerO != null) {
                                            Customer customer = (Customer) customerO;
                                    %>
                                    <input type="hidden" name="cust_id" value="<%= customer.getId()  %>">
                                    <% } %>
                                    <input type="hidden" name="prod_id" value="<%= product.getProduct_id()  %>">
                                    <input type="hidden" name="qty" value="1">
                                    <button type="submit" class="btn btn-primary">Add to cart</button>
                                </form>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                } else {
                %>
                <p>Products not found.</p>
                <%
                    }
                %>
            </div>

            <%-- Pagination--%>
            <nav class="mt-4" aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                    <li class="page-item <%= currentPage == 1 ? "disabled" : "" %>">
                        <a class="page-link" href="?page=<%= Math.max(1,currentPage-1 ) %>" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <%
                        for (long i = 1; i <= pages; ++i) {
                    %>
                    <li class="page-item <%= currentPage == i ? "active" : "" %>"><a class="page-link" href="?page=<%= i %>"><%= i %></a></li>
                    <% } %>
                    <li class="page-item <%= currentPage == pages ? "disabled" : "" %>">
                        <a class="page-link" href="?page=<%= Math.min(pages, currentPage + 1) %>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </section>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="./js/toast.js"></script>
    <script>
        const toastType = '<%= session.getAttribute("toast-type") %>'
        const toastMessage = '<%= session.getAttribute("toast-message") %>'

        <%
          session.removeAttribute("toast-type");
          session.removeAttribute("toast-message");
        %>

        if (toastType !== 'null')
            addToast(toastType, toastMessage);

    </script>
    </body>
</html>
