/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * invoices module
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojvalidation', 'ojs/ojknockout', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojcollectiontabledatasource', 'ojs/ojmodel'
], function (oj, ko, $) {
    /**
     * The view model for the main content view template
     */
    var invoicesContentViewModel = function () {

        const customerId = "CGN4711";
        const username = "Mr. John Doe";
        const apiKey = "67d0706a-9d52-4ed0-9812-3c01928d1074";
        //const serviceUrl = "http://oc-129-156-113-240.compute.oraclecloud.com:8011/financials-api/v1"
        //const serviceUrl = "http://132.145.236.64:80/api/financials"
        const serviceUrl = "http://financials-ms:7777/api/financials"
        var self = this;

        self.getHeaders = function() {
            return {
              'headers': {
                'api-key': apiKey
              }
            };
        }

        self.username = ko.observable("");
        self.customerIdentifier = ko.observable("");
        self.customerAccountBalance = ko.observable("0");
        self.customerAccountCurrency = ko.observable("EUR");

        var rootViewModel = ko.dataFor(document.getElementById('globalBody'));

        function updateModelFromGlobalContext(globalContext) {
            var customer = globalContext.customer;
            if (customer) {
                self.username(customer.title + " " + customer.firstName + " " + customer.lastName);
                self.customerIdentifier(customer.customerIdentifier);
            }
            else {
                self.username(username);
                self.customerIdentifier(customerId);
            }
        }

        rootViewModel.registerGlobalContextListener(
                function (globalContext) {
                    console.log("Financials - global context listener - receiving global context " + JSON.stringify(globalContext));
                    updateModelFromGlobalContext(globalContext);
                }
        );

        self.customerAccount = new oj.Model();

        dateConverter = function(dateTimeValue) {
            var options = {pattern: 'yyyy-MM-dd'};
            var converterFactory = oj.Validation.converterFactory("datetime");
            converter = converterFactory.createConverter(options);

            return converter.format(dateTimeValue)
        }

        var model = oj.Model.extend({
            parse: function (request) {
                return {
                    invoice_id: request.invoice_id,
                    order_id: request.order_id,
                    order_date: dateConverter(request.order_date),
                    total_price: request.total_price,
                    currency: request.currency,
                    payment_status: request.payment_status
                }
            },
            idAttribute: 'invoice_id'
        });

        var collection = new oj.Collection(null, {
            url: serviceUrl + "/invoices?customer_id=" + self.customerIdentifier(),
            fetchSize: 15,
            model: model,
            customURL: self.getHeaders
        });

        self.customerAccountModel = function () {

            var Customer = oj.Model.extend({
                url: serviceUrl + "/customers/"  + self.customerIdentifier() + "/account",
                parse: function (request) {
                    return {
                        customer_no: request.customer_no,
                        first_name: request.first_name,
                        last_name: request.last_name,
                        balance: request.balance,
                        currency: 'EUR'
                    };
                },
                idAttribute: '_id',
                customURL: self.getHeaders
            });
            return new Customer();
        };

        self.customerAccountModel().fetch({
            success: function (model, response, options) {
                console.log('Financials - Model for CustomerAccount loaded!' + response['first_name']);

                if(!self.customerIdentifier()) {
                    console.log('Financials - CustomerIdentifier not set yet! Applying the default from the received CustomerAccount!');
                    self.customerIdentifier(response['customer_no']);
                }

                if(!self.username()) {
                    console.log('Financials - Username not set yet! Applying the default from the received CustomerAccount!');
                    self.username(response['first_name'] + " " + response['last_name']);
                }

                self.customerAccountBalance(response['balance']);
            }
        });

        self.collection = collection;
        self.dataSource = ko.observableArray(collection);
        self.dataSource(new oj.CollectionTableDataSource(collection));
    };

    return invoicesContentViewModel;
});
