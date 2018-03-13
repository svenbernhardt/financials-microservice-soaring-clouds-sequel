/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * invoices module
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'ojs/ojknockout', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojcollectiontabledatasource', 'ojs/ojmodel'
], function (oj, ko, $) {
    /**
     * The view model for the main content view template
     */
    var invoicesContentViewModel = function () {

        var self = this;

        //self.serviceUrl = "http://localhost:7777/api/financials";
        self.serviceUrl = "http://129.150.114.134:7777/api/financials";

        self.username = ko.observable("");
        self.customerIdentifier = ko.observable("CGN4723");
        self.customerAccountBalance = ko.observable("");
        self.customerAccountCurrency = ko.observable("EUR");

        var rootViewModel = ko.dataFor(document.getElementById('globalBody'));

        function updateModelFromGlobalContext(globalContext) {
            var customer = globalContext.customer;
            if (customer) {
                self.username(customer.title + " " + customer.firstName + " " + customer.lastName);
                self.customerIdentifier(customer.customerIdentifier);
            }
        }

        rootViewModel.registerGlobalContextListener(
                function (globalContext) {
                    console.log("financials - global context listener - receiving global context " + JSON.stringify(globalContext));
                    updateModelFromGlobalContext(globalContext);
                }
        );

        self.customerAccount = new oj.Model();

        var model = oj.Model.extend({
            idAttribute: 'invoice_id'
        });

        var collection = new oj.Collection(null, {
            url: self.serviceUrl + "/invoices?customer_id=" + self.customerIdentifier(),
            fetchSize: 15,
            model: model
        });
        
        self.customerAccountModel = function () {

            var Customer = oj.Model.extend({
                url: self.serviceUrl + "/customers/"  + self.customerIdentifier() + "/account",
                parse: function (request) {
                    return {
                        customer_no: request.customer_no,
                        first_name: 'request.first_name',
                        last_name: request.last_name,
                        balance: request.balance,
                        currency: 'EUR'
                    };
                },
                idAttribute: '_id'
            });
            return new Customer();
        };

        var bla = self.customerAccountModel().fetch({
            success: function (model, response, options) {
                console.log('Model for CustomerAccount loaded!' + response['first_name']);
                
                if(!self.customerIdentifier().value) {
                    self.customerIdentifier(response['customer_no']);
                }
                
                if(!self.username().value) {
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
