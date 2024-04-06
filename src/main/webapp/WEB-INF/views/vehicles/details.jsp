<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">
        <%@ include file="/WEB-INF/views/common/header.jsp" %>
        <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
        <div class="content-wrapper">
            <section class="content">
                <div class="row">
                    <div class="col-md-3">
                        <div class="box box-primary">
                            <div class="box-body box-profile">
                                <h3 class="profile-username text-center">${vehicle.modele} ${vehicle.constructeur}</h3>
                                <ul class="list-group list-group-unbordered">
                                    <li class="list-group-item"><b>Reservation(s)</b> <a class="pull-right">${reservations.size()}</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="nav-tabs-custom">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#reservations" data-toggle="tab">Réservations</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="active tab-pane" id="reservations">
                                    <div class="box-body no-padding">
                                        <table class="table table-striped">
                                            <tr>
                                                <th style="width: 10px">#</th>
                                                <th>ID Client</th>
                                                <th>Date de debut</th>
                                                <th>Date de fin</th>
                                            </tr>
                                            <c:forEach var="reservation" items="${reservations}">
                                                <tr>
                                                    <td>${reservation.id}</td>
                                                    <td>${reservation.client_id}</td>
                                                    <td>${reservation.debut}</td>
                                                    <td>${reservation.fin}</td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
    </div>
    <%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
