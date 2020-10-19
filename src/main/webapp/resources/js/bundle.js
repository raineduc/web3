(function () {
    'use strict';

    class Point {
        /**
         * @param {number} x - X coordinate
         * @param {number} y - Y coordinate
         * @param {Boolean} hit - is hit
         */
        constructor(x, y, hit) {
            this.y = y;
            this.x = x;
            this.hit = hit;
        }

        /**
         * @returns {number} X
         */
        getX() {
            return this.x;
        }

        /**
         * @returns {number} Y
         */
        getY() {
            return this.y;
        }

        isHit() {
            return this.hit;
        }
    }

    const canvas = document.querySelector('.game-area__image');
    const ctx = canvas.getContext('2d');

    const tipWidth = 10;
    const tipHeight = Math.tan(Math.PI / 6) * tipWidth;

    const SUCCESS_COLOR = "#4caf50";
    const FAILURE_COLOR = "#f44336";

    const colors = ["#4a148c", "#1890ff", "#b71c1c", "#00c853", "#f57f17"];

    const draw = (radiusValues, points) => {
        const width = canvas.width;
        const height = canvas.height;
        ctx.font = "400 14px Roboto";
        ctx.clearRect(0, 0, width, height);
        drawCoordSystem(radiusValues);
        ctx.globalCompositeOperation = "source-over";
        ctx.fillStyle = "#000000";
        if (points && points.length) {
            drawPoints(points, Math.max(...radiusValues));
        }
    };

    const drawPoints = (points, maxRadius) => {
        points.forEach(point => {
            ctx.beginPath();
            const newPoint = translateRCoordsToCanvasCoords(point, maxRadius);
            ctx.save();
            ctx.fillStyle = point.isHit() ? SUCCESS_COLOR : FAILURE_COLOR;
            ctx.arc(newPoint.getX(), newPoint.getY(), 5, 0, Math.PI * 2);
            ctx.fill();
            ctx.restore();
            ctx.closePath();
        });
    };

    const translateCanvasCoordsToRCoords = (point, maxRadius) => {
        const scaleX = canvas.width / canvas.clientWidth;
        const scaleY = canvas.height / canvas.clientHeight;
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const lengthToRadiusMark = calculateLengthToRadiusMark(maxRadius, maxRadius);
        const newX = (point.getX() * scaleX - centerX) / lengthToRadiusMark * maxRadius;
        const newY = (centerY - point.getY() * scaleY) / lengthToRadiusMark * maxRadius;
        return new Point(newX, newY, point.isHit());
    };

    const translateRCoordsToCanvasCoords = (point, maxRadius) => {
        const scaleX = canvas.width / canvas.clientWidth;
        const scaleY = canvas.height / canvas.clientHeight;
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;
        const lengthToRadiusMark = calculateLengthToRadiusMark(maxRadius, maxRadius);
        const newX = (point.getX() * lengthToRadiusMark / maxRadius + centerX) / scaleX;
        const newY = (centerY - point.getY() * lengthToRadiusMark / maxRadius) / scaleY;
        return new Point(newX, newY, point.isHit());
    };

    function drawCoordSystem(radiusValues) {
        const width = canvas.width;
        const height = canvas.height;
        ctx.beginPath();
        ctx.moveTo(5, height / 2);
        ctx.lineTo(width - 5, height / 2);
        ctx.lineTo(width - 5 - tipWidth, height / 2 - tipHeight);
        ctx.moveTo(width - 5, height / 2);
        ctx.lineTo(width - 5 - tipWidth, height / 2 + tipHeight);
        ctx.moveTo(width / 2, height - 5);
        ctx.lineTo(width / 2, 5);
        ctx.lineTo(width / 2 + tipHeight, 5 + tipWidth);
        ctx.lineTo(width / 2, 5);
        ctx.lineTo(width / 2 - tipHeight, 5 + tipWidth);
        ctx.stroke();
        ctx.closePath();

        if (radiusValues && radiusValues.length) {
            const maxRadius = Math.max(...radiusValues);
            let alpha = 0.7;
            radiusValues.forEach((radius, index) => {
                drawRadiusMarks(radius, maxRadius);
                drawArea(radius, maxRadius, colors[index > 4 ? 4 : index], alpha);
                alpha -= 0.1;
            });
        }
    }
    function drawRadiusMarks(radius, maxRadius) {
        const width = canvas.width;
        const height = canvas.height;
        const lengthToRadiusMark = calculateLengthToRadiusMark(radius, maxRadius);
        const centerX = width / 2;
        const centerY = height / 2;

        let textRadius = fitFloatNumber(radius);

        ctx.beginPath();
        ctx.moveTo(centerX + lengthToRadiusMark, centerY + 5);
        ctx.lineTo(centerX + lengthToRadiusMark, centerY - 5);
        ctx.fillText(textRadius, centerX + lengthToRadiusMark - 10, centerY + 15);
        ctx.moveTo(centerX + lengthToRadiusMark / 2, centerY + 5);
        ctx.lineTo(centerX + lengthToRadiusMark / 2, centerY - 5);
        ctx.moveTo(centerX - lengthToRadiusMark, centerY + 5);
        ctx.lineTo(centerX - lengthToRadiusMark, centerY - 5);
        ctx.fillText("-" + textRadius, centerX - lengthToRadiusMark - 10, centerY + 15);
        ctx.moveTo(centerX - lengthToRadiusMark / 2, centerY + 5);
        ctx.lineTo(centerX - lengthToRadiusMark / 2, centerY - 5);
        ctx.moveTo(centerX + 5, centerY - lengthToRadiusMark);
        ctx.lineTo(centerX - 5, centerY - lengthToRadiusMark);
        ctx.fillText(textRadius, centerX + 10, centerY - lengthToRadiusMark + 5);
        ctx.moveTo(centerX + 5, centerY - lengthToRadiusMark / 2);
        ctx.lineTo(centerX - 5, centerY - lengthToRadiusMark / 2);
        ctx.moveTo(centerX + 5, centerY + lengthToRadiusMark);
        ctx.lineTo(centerX - 5, centerY + lengthToRadiusMark);
        ctx.fillText("-" + textRadius, centerX + 10, centerY + lengthToRadiusMark + 5);
        ctx.moveTo(centerX + 5, centerY + lengthToRadiusMark / 2);
        ctx.lineTo(centerX - 5, centerY + lengthToRadiusMark / 2);
        ctx.stroke();
        ctx.closePath();
    }

    function drawArea(radius, maxRadius, color, alpha) {
        const width = canvas.width;
        const height = canvas.height;
        const lengthToRadiusMark = calculateLengthToRadiusMark(radius, maxRadius);
        const centerX = width / 2;
        const centerY = height / 2;

        ctx.save();
        ctx.globalAlpha = alpha;
        ctx.fillStyle = color;
        ctx.beginPath();
        ctx.arc(centerX, centerY, lengthToRadiusMark, 0, Math.PI / 2);
        ctx.lineTo(centerX, centerY + lengthToRadiusMark / 2);
        ctx.lineTo(centerX - lengthToRadiusMark / 2, centerY);
        ctx.fill();
        ctx.closePath();
        ctx.fillRect(centerX, centerY - lengthToRadiusMark, lengthToRadiusMark, lengthToRadiusMark);
        ctx.restore();
    }

    function fitFloatNumber(num) {
        if (num.toString().search(/[\.\,]\d{2,}/) !== -1) {
            return num.toFixed(2).toString();
        }
        return num.toString();
    }

    function calculateLengthToRadiusMark(radius, relativeMaxRadius) {
        const width = canvas.width;
        const height = canvas.height;
        const maxLength = width / 2 - 10 - tipWidth;
        return radius / relativeMaxRadius * maxLength;
    }

    const canvas$1 = document.querySelector('.game-area__image');

    const radiusSlider = document.querySelector(".game-area__slider");

    const xInput = document.querySelector(".game-area__x-coord");
    const yInput = document.querySelector(".game-area__y-coord");
    const radiusInput = document.querySelector(".game-area__radius");

    let canvasHits = [];

    let radius = parseInt(radiusSlider.value);


    // Should be loaded by JSF (function's declarated in form.xhtml)
    const _jsf_sendHit = window._jsf_sendHit || function (array) {
    };

    window._jsf_handleAddPointsCallback = function (xhr, status, args) {
        if (args && args.hits) {
            canvasHits = canvasHits.concat(args.hits.map(hit => new Point(hit.x, hit.y, hit.result)));
            draw([radius], canvasHits);
        }
    };

    radiusSlider.addEventListener("input", (e) => {
        radius = parseInt(radiusSlider.value);
        draw([radius], canvasHits);
    });

    canvas$1.addEventListener("click", event => {

        const point = new Point(event.offsetX, event.offsetY);
        const pointInArea = translateCanvasCoordsToRCoords(point, [radius]);
        xInput.value = `${pointInArea.getX()}`;
        yInput.value = `${pointInArea.getY()}`;
        radiusInput.value = `${radius}`;
        _jsf_sendHit();
        // const pointInArea = translateCanvasCoordsToRCoords(point, Math.max(...radiusInputs));
        // coordInput.value = String(pointInArea.getY());
        // const nearestAllowedX = allowedXValues.reduce((acc, x) =>
        //     Math.abs(x - pointInArea.getX()) < Math.abs(acc - pointInArea.getX()) ? x : acc, Infinity);
        // document.querySelector(`.game-form__coord-button[data-value="${nearestAllowedX}"]`).click();
    });

    draw([radius]);

}());
