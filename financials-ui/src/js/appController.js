define(['ojs/ojcore', 'knockout', 'ojs/ojknockout', 'ojs/ojrouter'],
        function (oj, ko) {
            function ControllerViewModel() {
                var self = this;

                self.globalContext = {};

                self.invoices = ko.observable("invoices");
               
                self.init = function () {

                    console.log('In init...');
                };

                // Media queries for repsonsive layouts
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);

                self.appName = ko.observable("Financials area");
                
                $(document).ready(function () {

                    self.init();

                });
            }

            return new ControllerViewModel();
        }
);