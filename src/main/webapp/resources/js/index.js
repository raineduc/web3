import {draw as drawArea, translateCanvasCoordsToRCoords} from "./game-area/game-area";
import {Point} from "./game-area/point";

const canvas = document.querySelector('.game-area__image');

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
        drawArea([radius], canvasHits);
    }
}

radiusSlider.addEventListener("input", (e) => {
    radius = parseInt(radiusSlider.value);
    drawArea([radius], canvasHits);
})

canvas.addEventListener("click", event => {

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

drawArea([radius]);