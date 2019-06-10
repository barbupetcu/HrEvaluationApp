(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeControllerMan', HomeControllerMan);

    HomeControllerMan.$inject = ['DataService', 'UserService', '$rootScope', '$interval', '$q', '$http'];
    function HomeControllerMan(DataService, UserService, $rootScope, $interval, $q, $http) {
        var vm = this;
        vm.setSelectedId=setSelectedId;

        (function initController() {
            loadDisabledUsers();
            loadHome();
            
        })();

        function loadDisabledUsers() {
            UserService.LoadDisabledUsers($rootScope.globals.currentUser.dept)
            .then(function (response){
                if(response){
                    vm.disabledUsers = response
                }
            });
        };

        function setSelectedId(selectedId) {
            DataService.setSelectedId(selectedId);
        };

        function loadHome(){
            UserService.loadHomeManager($rootScope.globals.currentUser.dept)
            .then(function (response){
                if(response){
                    vm.myTasks = response.myTasks;
                    vm.countTasks = response.countTasks;
                    vm.deptUsers = response.deptUsers;
                }
            });
        };

        vm.exportaDetalii = function exportaDetalii(){
            var deferred = $q.defer();
            var defaultFileName='test.xlsx';
            $http.get('/api/exportDetails', {params:{id: $rootScope.globals.currentUser.dept}, responseType: "arraybuffer" }).then(
                function (response) {
                    var type = response.headers('Content-Type');
                    var disposition = response.headers('Content-Disposition');
                    if (disposition) {
                        var match = disposition.match(/.*filename=\"?([^;\"]+)\"?.*/);
                        if (match[1])
                            defaultFileName = match[1];
                    }
                    defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
                    var blob = new Blob([response.data], { type: type });
                    saveAs(blob, defaultFileName);
                    deferred.resolve(defaultFileName);
                }, function (data, status) {
                    var e = /* error */
                        deferred.reject(e);
                });
            return deferred.promise;
        }
    }

})();