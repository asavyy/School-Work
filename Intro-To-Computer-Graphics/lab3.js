// ------------------------------------------------------------
// WebGL Lab 3 - Template
// ------------------------------------------------------------

var VSHADER = 
`
attribute vec2 a_Position;
void main() {
  gl_Position = vec4(a_Position, 0.0, 1.0);
  gl_PointSize = 4.0;
}
`

var FSHADER =
`
void main() {
  gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);
}
`
// global context object to store everything.
let context = { };
var mousePositions = [ ]; //This array stores clicked mouse positions. 
var i = 0; // integer to keep track of clicks
/*
    render function
      - currently called on every mouse click
*/
function render(context) {

  let gl = context.gl; // shortcut

  // set clear color and clear frame buffer
  gl.clearColor(0.9,0.9,1.0,1.0);
  gl.clear(gl.COLOR_BUFFER_BIT);

  if (i % 3 != 0 || i == 0) {
// pushes our current mouse x,y to the end of our array, aka the first click is our first vertex.
    temp = mousePositions.push(context.mouse.x);
    temp = mousePositions.push(context.mouse.y);
    i++;
  }
  else {
//this is what actually creates the triangle, as instructed in the pdf
    let vertices = new Float32Array([mousePositions.shift(), mousePositions.shift(), mousePositions.shift(), mousePositions.shift(), mousePositions.shift(), mousePositions.shift()]);
    console.log(vertices);
    let buffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
    gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);
    gl.vertexAttribPointer(gl.a_Position, 2, gl.FLOAT, false, 0, 0);
    gl.enableVertexAttribArray(gl.a_Position);


    gl.drawArrays(gl.TRIANGLES, 0, 3);
    i = 0;
  }
}

/*
    mouse handler function 
      - gets mouse coordinates in browser client area coordinates
      - handler transforms from client area coordinates
           to WebGL normalized device coordinates
*/
function handle_mouse(ev, canvas, context) {
  let rect = ev.target.getBoundingClientRect();
  let mx = ev.clientX;    // x mouse
  let my = ev.clientY;    // y mouse
  let cx = rect.left;     // canvas position x
  let cy = rect.top;      // canvas position y
  let wx = canvas.width;  // canvas width x
  let wy = canvas.height; // canvas width y

  // transform in matrix-vector notation
  //
  // ┏ 1  0 ┓   ┏ 2/w_x       0 ┓ ┏ m_x - c_x ┓   ┏ - 1.0 ┓
  // ┗ 0 -1 ┛ ( ┗      0  2/w_y ┛ ┗ m_y - c_y ┛ + ┗ - 1.0 ┛ )
  //
  let x_premul =  2.0 / wx;
  let y_premul = -2.0 / wy;
  let screen_x = x_premul*(mx-cx) - 1.0;
  let screen_y = y_premul*(my-cy) + 1.0;

  context.mouse = { x : screen_x, y : screen_y };
  render(context);
}

/*
    initialization function
      - gets WebGL context from canvas object
      - initializes shaders via external library function
      - gets attribute location
      - stores WebGL context and additional information
          in global context object
      - registers mouse handler
      - clears framebuffer
*/
function canvas_init3D() {
  let canvas = document.querySelector("#canvas_gl");
  
  let gl = canvas.getContext("webgl");
  if (!gl) {
    console.log("Failed to get rendering context for WebGL");
    return;
  }
  
  if (!initShaders(gl, VSHADER, FSHADER)) {
    console.log("Failed to initialize shaders.");
    return;
  }

  var a_Position = gl.getAttribLocation(gl.program, "a_Position");
  if (a_Position < 0) {
    console.log("Failed to get storage location of a_Position.");
    return;
  }

  // store information in context
  context.gl = gl;
  context.a_Position = a_Position;

  // register mouse listener using anonymous function
  canvas.onmousedown = function(ev) {
    handle_mouse(ev, canvas, context);
  };

  gl.clearColor(0.9,0.9,1.0,1.0);
  gl.clear(gl.COLOR_BUFFER_BIT);
}

https://pitt.zoom.us/j/839428065

function main() {
  console.log("WebGL - Lab3");
  canvas_init3D();
}