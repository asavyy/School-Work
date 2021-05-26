// ------------------------------------------------------------
// WebGL Lab 4 - Transformations
// ------------------------------------------------------------

var VSHADER = 
`
precision highp float;
attribute vec2 a_Position;
attribute float a_Value;
uniform mat3 u_H3;
varying float v_Value;
void main() {
  mat4 H = mat4(
    u_H3[0].xy, 0.0, u_H3[0][2],
    u_H3[1].xy, 0.0, u_H3[1][2],
    0.0,   0.0, 1.0, 0.0,
    u_H3[2].xy, 0.0, u_H3[2][2]
  );  
  gl_Position = H * vec4(a_Position, 0.0, 1.0);
  gl_PointSize = 4.0;
  v_Value = a_Value;
}
`

var FSHADER =
`
precision highp float;
uniform int u_Mode;
varying float v_Value;
void main() {
  if (u_Mode == 0) {
    gl_FragColor = vec4(0.5, 0.5, 1.0, 1.0);  
  } else if (u_Mode == 1) {
    gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
  }
  
}
`

function transpose_mat3(mat3) {
  // transpose matH3 to column major
  for (let i=1;i<3; ++i) {
    for (let j=0;j<i; ++j) {
      // use direct access (i*3+j)
      let h = mat3[i*3+j];
      mat3[i*3+j] = mat3[j*3+i];
      mat3[j*3+i] = h;
    }
  } 
}

// global context object to store everything 
// ( we basically just throw everything that we 
//  need into this object ... no need for software 
//  engineering at this point :) )
let context = { 
    animation_on : false
};

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
  context.animation_on = !context.animation_on;
  
  // currently, no explicite update calls
}

function init_mousehandler(context, canvas) {
  canvas.onmousedown = function(ev) {
      handle_mouse(ev, canvas, context);
  };
}

function init_buffers(context) {
  let ctx = context;    // shortcut alias
  let gl = context.gl;  // shortcut alias

  ctx.buffer_house = gl.createBuffer();
  ctx.buffer_wires = gl.createBuffer();
  ctx.vertices_house = new Float32Array([
      0.0, 0.0,  0.2, 0.0,  0.2, 0.2,
      0.0, 0.0,  0.2, 0.2,  0.0, 0.2,
      0.0, 0.2,  0.2, 0.2,  0.1, 0.3
  ]);
  ctx.vertices_wires = new Float32Array([
      0.0, 0.0,  0.2, 0.0,  0.2, 0.0,  0.2, 0.2,  0.2, 0.2, 0.0, 0.0,
      0.0, 0.0,  0.2, 0.2,  0.2, 0.2,  0.0, 0.2,  0.0, 0.2, 0.0, 0.0, 
      0.0, 0.2,  0.2, 0.2,  0.2, 0.2,  0.1, 0.3,  0.1, 0.3, 0.0, 0.2
  ]);

  ctx.matH3 = new Float32Array([
    1.0, 0.5, 0.5,
    0.0, 1.0, -1.0,
    0.0, 0.0, 1.0,
  ]);

  transpose_mat3(ctx.matH3);
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

  let a_Position = gl.getAttribLocation(gl.program, "a_Position");
  if (a_Position < 0) {
    console.log("Failed to get storage location of a_Position.");
    return;
  }

  let a_Value = gl.getAttribLocation(gl.program, "a_Value");
  if (a_Value < 0) {
    console.log("Failed to get storage location of a_Value.");
    return;
  }  

  let u_Mode = gl.getUniformLocation(gl.program, "u_Mode");
  if (u_Mode < 0) {
    console.log("Failed to get storage location of u_Mode.");
    return;
  }

  let u_H3 = gl.getUniformLocation(gl.program, "u_H3");
  if (u_H3 < 0) {
    console.log("Failed to get storage location of u_H3.");
    return;
  }

  // store information in context
  context.gl = gl;
  context.a_Position = a_Position;
  context.a_Value = a_Value;
  context.u_Mode = u_Mode;
  context.u_H3 = u_H3;

  // initialize buffers
  init_buffers(context);

  // register mouse handler
  init_mousehandler(context, canvas);

  // clear framebuffer
  gl.clearColor(0.9,0.9,1.0,1.0);
  gl.clear(gl.COLOR_BUFFER_BIT);
}

/* 
 * The update() function updates the model
 * that you want to render; it changes the
 * state of the model.
 */
function update(context, timestamp) {
  let ctx = context;    // shortcut alias
  let gl = context.gl;  // shortcut alias
    
  if (!ctx.timestamp_last) {
    console.log("no last time stamp");
    ctx.timestamp_last = timestamp;
    ctx.angle = 0.0;
    ctx.speed = 20.0; // degree per second
    ctx.animation_on = false;
    return;
  }

  if (!ctx.animation_on) {
    return;
  }

  // get timestamp last and update in context
  let timestamp_last = ctx.timestamp_last
  ctx.timestamp_last = timestamp;

  let d_sec = (timestamp - timestamp_last) / 1000.0;
 
  ctx.angle += d_sec * ctx.speed;
  console.log(ctx.angle);

  let angle =  ctx.angle * (Math.PI / 180.0);
  let cosA = Math.cos(angle) * .5;
  let sinA = Math.sin(angle) * .5;
  let tx = 0.0;
  let ty = 0.0;
  if (ctx.mouse) {
    tx = ctx.mouse.x;
    ty = ctx.mouse.y;
  }
//begin coding here.
// need at least two matricies here
// basic structure is matH3 = T2 * T1
//write something to multiply matricies
//H3[0] = T2[0]*T1[0] + ...
  ctx.matH3.set([
    cosA, sinA, cosA,
    -sinA,  cosA, sinA,
    0.0,    0.0, 1.0,
  ]);

  transpose_mat3(ctx.matH3);
}

/* 
 * The render function issues the draw calls
 * based on the current state of the model.
 */
function render(context, timestamp) {
  let ctx = context;    // shortcut alias
  let gl = context.gl;  // shortcut alias

  // clear framebuffer
  gl.clearColor(0.9,0.9,1.0,1.0);
  gl.clear(gl.COLOR_BUFFER_BIT);

  // use vertices from buffer
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.buffer_house);
  gl.bufferData(gl.ARRAY_BUFFER, ctx.vertices_house, gl.STATIC_DRAW);
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.buffer_wires);
  gl.bufferData(gl.ARRAY_BUFFER, ctx.vertices_wires, gl.STATIC_DRAW);

  // set matrix data
  gl.uniformMatrix3fv(ctx.u_H3, false, ctx.matH3);

  // draw triangles
  gl.uniform1i(ctx.u_Mode, 0);
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.buffer_house);
  gl.vertexAttribPointer(ctx.a_Position, 2, gl.FLOAT, false, 0, 0);
  gl.enableVertexAttribArray(ctx.a_Position);
  gl.vertexAttrib1f(ctx.a_Value, 1.0);
  gl.drawArrays(gl.TRIANGLES, 0, 9);

  // draw wireframe mesh
  gl.uniform1i(ctx.u_Mode, 1);
  gl.bindBuffer(gl.ARRAY_BUFFER, ctx.buffer_wires);
  gl.vertexAttribPointer(ctx.a_Position, 2, gl.FLOAT, false, 0, 0);
  gl.enableVertexAttribArray(ctx.a_Position);
  gl.vertexAttrib1f(ctx.a_Value, 0.0);
  gl.drawArrays(gl.LINES, 0, ctx.vertices_wires.length/2);
}

/* 
 * The step() function is called for each animation
 * step. Note that the time points are not necessarily
 * equidistant.
 */
function step(timestamp) {
  update(context, timestamp);
  render(context);
  window.requestAnimationFrame(step);
}

function main() {
  console.log("WebGL - Lab4");

  window.requestAnimationFrame(step);
  canvas_init3D();
}

