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
    function invoicesContentViewModel() {

        var self = this;

        self.serviceUrl = "http://localhost:7777/api/financials";
        //self.serviceUrl = "http://129.150.114.134:7777/api/financials";

        self.customerAccount = new oj.Model();

        var model = oj.Model.extend({
            idAttribute: 'invoice_id'
        });

        var collection = new oj.Collection(null, {
            url: self.serviceUrl + "/invoices",
            fetchSize: 15,
            model: model
        });

        var customerAccountModel = function () {

            var Customer = oj.Model.extend({
                url: self.serviceUrl + "/customers/CGN4712/account",
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

        self.customerAccount = new customerAccountModel().fetch({
            success: function (model, response, options) {
                console.log('Model for CustomerAccount loaded!' + response['first_name']);

            }
        });
        
        self.ca = ko.observable(self.customerAccount);

        self.collection = collection;
        self.dataSource = ko.observableArray(collection);
        self.dataSource(new oj.CollectionTableDataSource(collection));
    }

    return invoicesContentViewModel;
});
