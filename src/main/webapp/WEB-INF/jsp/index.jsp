<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Оптимизация коммерческих закупок</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
</head>
<body>
<main role="main" class="container">
    <div class="card">
        <div class="card-header">
            <h5 class="my-2">Список поставщиков</h5>
        </div>
        <div class="card-body">
            <table class="table table-sm">
                <thead>
                <tr class="d-flex">
                    <th class="col">Имя</th>
                    <th class="col">Продукт</th>
                    <th class="col">В наличии</th>
                    <th class="col">Цена за ед.</th>
                    <th class="col">Вместительность <br> поддона</th>
                    <th class="col">Вместительность <br> машины</th>
                    <th class="col">Цена за машину</th>
                    <th class="col">Удалить</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${suppliers}" var="supplier">
                    <tr class="d-flex">
                        <td class="col">${supplier.name}</td>
                        <td class="col">${supplier.product}</td>
                        <td class="col">${supplier.quantity}</td>
                        <td class="col">${supplier.unitPrice}</td>
                        <td class="col">${supplier.capacityPallets}</td>
                        <td class="col">${supplier.capacityOfCar}</td>
                        <td class="col">${supplier.carPrice}</td>
                        <td class="col">
                            <form method="post" action="/delete">
                                <input type="text" name="id" hidden readonly value="${supplier.id}">
                                <button type="submit" class="btn btn-outline-danger">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <form action="/add" method="post" class="my-2 card">
        <div class="card-header">
            <h6>Добавить поставщика</h6>
        </div>
        <div class="card-body">
            <div class="row mb-2">
                <div class="col-sm">
                    <label class="form-label">Имя</label>
                    <input required type="text" class="form-control" name="name">
                </div>
                <div class="col-sm">
                    <label class="form-label">Продукт</label>
                    <input required type="text" class="form-control" name="product">
                </div>
                <div class="col-sm">
                    <label class="form-label">В наличии</label>
                    <input required type="number" class="form-control" name="quantity">
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-sm">
                    <label class="form-label">Цена за ед.</label>
                    <div class="input-group">
                        <input required type="number" class="form-control" name="unitPrice">
                        <span class="input-group-text">₽</span>
                    </div>
                </div>
                <div class="col-sm">
                    <label class="form-label">Вместительность поддона</label>
                    <input required type="number" class="form-control" name="capacityPallets">
                </div>
                <div class="col-sm">
                    <label class="form-label">Вместительность машины</label>
                    <input required type="number" class="form-control" name="capacityOfCar">
                </div>
                <div class="col-sm">
                    <label class="form-label">Цена за машину</label>
                    <div class="input-group">
                        <input required type="number" class="form-control" name="carPrice">
                        <span class="input-group-text">₽</span>
                    </div>
                </div>
            </div>
            <div class="row d-grid gap-2 col-3 mx-auto">
                <button type="submit" class="btn btn-outline-primary" value="add">Добавить</button>
            </div>
        </div>
    </form>

    <div class="row justify-content-md-center">
        <div class="col-sm-4 card m-2 px-0">
            <div class="card-header">
                <h6>Коммерческий запрос</h6>
            </div>
            <form class="card-body" action="/query" method="post">
                <fieldset>
                    <div class="form-group row mb-2 justify-content-between">
                        <div class="col-4"><label for="fullOrder">Количество</label></div>
                        <div class="col-8"><input type="number" name="fullOrder" id="fullOrder"></div>
                    </div>
                    <div class="form-group row mb-2 justify-content-between">
                        <div class="col-4"><label for="product">Продукт</label></div>
                        <div class="col-8">
                            <select id="product" class="form-select form-select-sm" name="product">
                                <c:forEach items="${listProduct}" var="product">
                                    <option value="${product}">${product}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row mb-2 justify-content-between">
                        <div class="col-4">
                            <button type="submit" class="btn btn-outline-primary" value="query">Рассчитать</button>
                        </div>
                    </div>
                </fieldset>
            </form>
            <jsp:useBean id="orderList" scope="request" type="java.util.ArrayList"/>
            <c:if test="${orderList.size() > 0}">
                <ul class="list-group list-group-flush">
                    <c:forEach items="${orderList}" var="order">
                        <li class="list-group-item">${order}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>
</main>
<footer class="footer mt-auto py-4 bg-light">
    <div class="container">
            <span class="text-muted">Выпускная квалификационная работа Попеляевой О.С.
            <br> Модель определения предпочтительных условий коммерческой закупки продукции при наличии ограничений со
            стороны Поставщика и Компании
            <br> Краснодар
            <br> 2021</span>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8"
        crossorigin="anonymous"></script>
</body>
</html>