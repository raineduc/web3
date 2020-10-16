import { draw as drawArea, translateCanvasCoordsToRCoords } from "./game-area";
import { Point } from "./point";

const canvas = document.querySelector('.game-area__image');

const radiusSlider = document.querySelector(".game-area__slider");

const xInput = document.querySelector(".game-area__x-coord");
const yInput = document.querySelector(".game-area__y-coord");
const radiusInput = document.querySelector(".game-area__radius");

const allowedXValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

let radius = parseInt(radiusSlider.value);


// Should be loaded by JSF (function's declarated in form.xhtml)
console.log(globalThis);

const _jsf_sendHit = window._jsf_sendHit || function (array) {};



radiusSlider.addEventListener("input", (e) => {
    radius = parseInt(radiusSlider.value);
    drawArea([radius]);
})

canvas.addEventListener("click", event => {

    const point = new Point(event.offsetX, event.offsetY);
    drawArea([parseInt(radiusSlider.value)], point);
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