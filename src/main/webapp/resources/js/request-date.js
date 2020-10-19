'use strict';

const date = new Date();
const offset = date.getTimezoneOffset();
document.cookie = "TIMEZONE_COOKIE=" + offset;

// Should be loaded by JSF (function's declarated in index.xhtml)
const _jsf_requestCurrentDate = window._jsf_requestCurrentDate || function () {};

setInterval(() => {
    _jsf_requestCurrentDate();
}, 5000);