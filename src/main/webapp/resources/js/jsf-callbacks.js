'use strict';

window._jsf_addPoints = function() {};

document.addEventListener('DOMContentLoaded', (e) => {
   window._jsf_addPoints = function (xhr, status, args) {
        _jsf_handleAddPointsCallback(xhr, status, args);
   }
});