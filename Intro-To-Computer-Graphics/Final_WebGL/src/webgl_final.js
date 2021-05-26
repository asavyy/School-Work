// main program

// global context object to store everything 
// ( we basically just throw everything that we 
//  need into this object ... no need for software 
//  engineering at this point :) )
let context = { };

function initRender3D(context) {
    let ctx = context;

    // get HTML canvas element
    let canvas = document.querySelector("#main_canvas");
    ctx.canvas = canvas;

    // get WebGL context
    let gl = canvas.getContext("webgl");
    ctx.gl = gl;
    if (gl == null) {
      console.error("Can't get WebGL context.");
      return;
    }

    // setup WebGL 
    gl.frontFace(gl.CCW);  // standard: GL_CCW
    gl.cullFace(gl.BACK);  // standard: GL_BACK
   // gl.enable(gl.CULL_FACE);

    gl.depthFunc(gl.LESS); // standard: GL_LESS
    gl.enable(gl.DEPTH_TEST);

    // setup view and projection matrices

    context.mat4_view = new Float32Array(16);
    context.mat4_projection = new Float32Array(16);
    context.mat4_VP = new Float32Array(16);

    context.viewer_azimuth = Math.PI * 0.25;
    context.viewer_altitude = Math.PI * 0.25;
    context.viewer_distance = 10.0;

    context.viewer_azimuth_down = context.viewer_azimuth;
    context.viewer_altitude_down = context.viewer_altitude;
    context.viewer_distance_down = context.viewer_distance;

    mat4_set_identity(context.mat4_view);
    mat4_set_identity(context.mat4_projection);
    mat4_set_identity(context.mat4_VP);
}

function initScene(context, scene) {
    let ctx = context;
    let gl = context.gl;

    // compile all shaders that are attached to scene
    for (const [name, program] of Object.entries(scene.programs)) {
        console.log("[info] compile program '" + name + "'");
        program.id = createProgram(gl, 
            program.vertex_shader.source, 
            program.fragment_shader.source);
        if (program.id == null) {
            console.log("[error] compiling program '" + name + "'");
            return false;
        }
        program.is_compiled = true;

        // get active attributes
        let n_attribs = gl.getProgramParameter(program.id, gl.ACTIVE_ATTRIBUTES);
        for (j=0; j<n_attribs; ++j) {
            let info = gl.getActiveAttrib(program.id, j);
            let loc = gl.getAttribLocation(program.id, info.name);
            console.log("  found attribute '" + info.name + "'");
            program.attributes[info.name] = loc;
        }

        // get active uniforms
        let n_uniforms = gl.getProgramParameter(program.id, gl.ACTIVE_UNIFORMS);
        for (j=0; j<n_uniforms; ++j) {
            let info = gl.getActiveUniform(program.id, j);
            let loc = gl.getUniformLocation(program.id, info.name);
            console.log("  found uniform '" + info.name + "'");
            program.uniforms[info.name] = loc;
        }
    }

    // create WebGL buffers for all geometries
    for (const [name, geometry] of Object.entries(scene.geometries)) {
        console.log("[info] creating buffers for geometry '" + name 
            + "' with " + geometry.primitives + " primitives");

        // create attribute buffers
        for (const [attribute_name, buffer] of Object.entries(geometry.buffers)) {
            console.log("  buffer for attribute '" + attribute_name + "'");
            let buffer_gl = gl.createBuffer();
            geometry.buffers_gl[attribute_name] = buffer_gl;
            gl.bindBuffer(gl.ARRAY_BUFFER, buffer_gl);
            gl.bufferData(gl.ARRAY_BUFFER, buffer, gl.STATIC_DRAW);
        }

        // create index (element) buffer
        if (geometry.elements) {
            console.log("  buffer for elements");
            let elements_gl = gl.createBuffer();
            geometry.elements_gl = elements_gl;
            gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, elements_gl);
            gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, geometry.elements, gl.STATIC_DRAW);
        }
    }

    return true;
}

function initMouseHandler(context) {
    context.is_mouse_down = false;
    context.mouse_down = [0.0, 0.0];
    context.mouse_move = [0.0, 0.0];
    context.mouse_wheel = 0.0;

    context.canvas.onmousedown = function(ev) {
        handleOnMouseDown(ev, context);
    };
    context.canvas.onmousemove = function(ev) {
        handleOnMouseMove(ev, context);
    };
    context.canvas.onmouseup = function(ev) {
        handleOnMouseUp(ev, context);
    };
    context.canvas.onmouseout = function(ev) {
        handleOnMouseUp(ev, context);
    };
    context.canvas.onmousewheel = function(ev) {
        handleOnMouseWheel(ev, context);
        return false;
    };
}

function initialize(scene) {
    console.log('[info] start initialize rendering')
    context.scene = scene;

    initRender3D(context);
    initScene(context, scene);
    initMouseHandler(context);

    console.log('[info] initialize rendering complete')
    window.requestAnimationFrame(step);
}

function transformClient2WebGL(canvas, mouse) {

    // transform in matrix-vector notation
    // c_wx, c_wy -- canvas width
    // c_x, c_y -- canvas position
    // m_x, m_y -- mouse position
    //
    // ┏ 1  0 ┓   ┏ 2/c_wx       0 ┓ ┏ m_x - c_x ┓   ┏ - 1.0 ┓
    // ┗ 0 -1 ┛ ( ┗      0  2/c_wy ┛ ┗ m_y - c_y ┛ + ┗ - 1.0 ┛ )
    //
    let x_premul =  2.0 / canvas[2];
    let y_premul = -2.0 / canvas[3];
    let x = x_premul*(mouse[0]-canvas[0]) - 1.0;
    let y = y_premul*(mouse[1]-canvas[1]) + 1.0;

    return [x, y];
}

function handleOnMouseDown(ev, context) {
    let rect = ev.target.getBoundingClientRect();

    context.is_mouse_down = true;
    context.mouse_down = transformClient2WebGL(
        [rect.left, rect.top, context.canvas.width, context.canvas.height], 
        [ev.clientX, ev.clientY]);
    context.mouse_move = context.mouse_down;

    context.viewer_azimuth_down = context.viewer_azimuth;
    context.viewer_altitude_down = context.viewer_altitude;
    context.viewer_distance_down = context.viewer_distance;
}

function handleOnMouseMove(ev, context) {
    let rect = ev.target.getBoundingClientRect();

    if (!context.is_mouse_down) {
        return;
    }

    context.mouse_move = transformClient2WebGL(
        [rect.left, rect.top, context.canvas.width, context.canvas.height], 
        [ev.clientX, ev.clientY]);
}

function handleOnMouseUp(ev, context) {
    let rect = ev.target.getBoundingClientRect();

    context.is_mouse_down = false;
}

function handleOnMouseOUT(ev, context) {
    let rect = ev.target.getBoundingClientRect();

    context.is_mouse_down = false;
}

function handleOnMouseWheel(ev, context) {
    context.mouse_wheel += ev.deltaY;
}

/* 
 * The update() function updates the model
 * that you want to render; it changes the
 * state of the model.
 */
function update(context, timestamp) {
    let ctx = context;    // shortcut alias
    let gl = context.gl;  // shortcut alias

    // lazy initialization
    if (!ctx.timestamp_last) {
        ctx.timestamp_last = timestamp;
        ctx.timestamp_init = timestamp;
        ctx.time = 0.0;
        ctx.angle = 0.0;
        ctx.speed = 20.0; // degree per second
        ctx.speed_zoom = 0.002;
        return;
    }

    // get timestamps and update context
    let ts_init = ctx.timestamp_init;  // initial timestamp in ms
    let ts_last = ctx.timestamp_last   // last timestamp in ms
    let ts_curr = timestamp;           // current timestamp in ms
    ctx.timestamp_last = timestamp;
    ctx.time = (timestamp - ctx.timestamp_init) * 0.001;

    // setup viewer
    context.viewer_distance -= 
            context.speed_zoom * context.mouse_wheel;
    context.mouse_wheel = 0.0;
    context.viewer_distance = 
        Math.max(1.0, Math.min(context.viewer_distance, 10.0)); 
    let dist = context.viewer_distance;
    let altitude = context.viewer_altitude;
    let azimuth = context.viewer_azimuth;
    
    if (context.is_mouse_down) {
      let speed_altitude = 1.0;
      let speed_azimuth = 1.0;
      dx = context.mouse_move[0] - context.mouse_down[0];
      dy = context.mouse_move[1] - context.mouse_down[1];
      altitude = context.viewer_altitude_down + speed_altitude * -dy;
      azimuth  = context.viewer_azimuth_down + speed_azimuth * dx;
      altitude = Math.max(-Math.PI*0.45, Math.min(Math.PI*0.45, altitude));
      context.viewer_altitude = altitude;
      context.viewer_azimuth = azimuth;
    }

    let cosAltitude = Math.cos(altitude);
    let sinAltitude = Math.sin(altitude);
    let cosAzimuth = Math.cos(azimuth);
    let sinAzimuth = Math.sin(azimuth);
    
    let eye0_x = cosAltitude * dist;
    let eye1_y = sinAltitude * dist;
    let eye1_x = cosAzimuth * eye0_x;
    let eye1_z = sinAzimuth * eye0_x;
    
    let eye = [eye1_x, eye1_y, eye1_z];
    let center = [0,0,0];
    let up = [0,1,0];

    // update scene

    mat4_set_orthogonal(ctx.mat4_projection, -10, 10, -10, 10, 100, -100);
    // console.log(ctx.mat4_projection);

    mat4_set_perspective(ctx.mat4_projection, 1.5, 1.0, 0.1, 100.0);
    // mat4_set_identity(ctx.mat4_projection);

    mat4_set_lookat(ctx.mat4_view, eye, center, up);
    mat4_mul_mat4(ctx.mat4_VP, ctx.mat4_projection, ctx.mat4_view);
    mat4_transpose(ctx.mat4_VP); // transpose before uploads row2col major
}

/* 
 * The render() function issues the draw calls
 * based on the current state of the model.
 */
function render(context) {
  let gl = context.gl;
  let ctx = context;
  let scene = context.scene;

  // clear framebuffer
  gl.clearColor(0.95, 0.95, 1.0, 1.0);
  gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    {   // render the gltf asset
        let prog = scene.programs.objects; // use program 'objects'
        let geom = scene.geometries.asset;
        let buf_gl_Position = geom.buffers_gl.a_Position;
        let buf_gl_Normal = geom.buffers_gl.a_Normal;
        let buf_gl_elements = geom.elements_gl;
        let k_elements = geom.elements.length;

        gl.useProgram(prog.id); // .id contains the WebGL identifier
        gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl_Position);
        gl.vertexAttribPointer(prog.attributes.a_Position, 3, gl.FLOAT, false, 0, 0);
        gl.enableVertexAttribArray(prog.attributes.a_Position);

        gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl_Normal);
        gl.vertexAttribPointer(prog.attributes.a_Normal, 3, gl.FLOAT, false, 0, 0);
        gl.enableVertexAttribArray(prog.attributes.a_Normal);

        //create model matrix
        let mat_t = new Float32Array(16);
        let mat_r = new Float32Array(16);
        let mat_rt = new Float32Array(16);
        let mvp = new Float32Array(16);
        mat4_set_identity(mat_t);
        mat4_set_col(mat_t, 3, [0.0, 0.0, 7.0, 1.0]);
        mat4_set_rotation_y(mat_r, .10 * context.time);
        mat4_mul_mat4(mat_rt, mat_r, mat_t);
        mat4_transpose(context.mat4_VP);
        mat4_mul_mat4(mvp, context.mat4_VP, mat_rt);

        mat4_transpose(mvp);
        mat4_transpose(context.mat4_VP, mat_rt);

        // set uniforms (uniforms are same fo all vertices)
        gl.uniform1f(prog.uniforms.u_Time, ctx.time);
        gl.uniform1i(prog.uniforms.u_Mode, 0);
        gl.uniformMatrix4fv(prog.uniforms.u_VP, false, mvp);

        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, buf_gl_elements);
        gl.drawElements(gl.TRIANGLES, k_elements, gl.UNSIGNED_SHORT, 0);
    }

    {   // render the gltf asset
            let prog = scene.programs.objects; // use program 'objects'
            let geom = scene.geometries.asset2;
            let buf_gl_Position = geom.buffers_gl.a_Position;
            let buf_gl_Normal = geom.buffers_gl.a_Normal;
            let buf_gl_elements = geom.elements_gl;
            let k_elements = geom.elements.length;

            gl.useProgram(prog.id); // .id contains the WebGL identifier
            gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl_Position);
            gl.vertexAttribPointer(prog.attributes.a_Position, 3, gl.FLOAT, false, 0, 0);
            gl.enableVertexAttribArray(prog.attributes.a_Position);

            gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl_Normal);
            gl.vertexAttribPointer(prog.attributes.a_Normal, 3, gl.FLOAT, false, 0, 0);
            gl.enableVertexAttribArray(prog.attributes.a_Normal);

            //create model matrix
            let mat_t = new Float32Array(16);
            let mat_r = new Float32Array(16);
            let mat_rt = new Float32Array(16);
            let mvp = new Float32Array(16);
            mat4_set_identity(mat_t);
            mat4_set_col(mat_t, 3, [0.0, 0.0, 0.0, 1.0]);
            mat4_set_rotation_y(mat_r, .10 * context.time);
            mat4_mul_mat4(mat_rt, mat_r, mat_t);
            mat4_transpose(context.mat4_VP);
            mat4_mul_mat4(mvp, context.mat4_VP, mat_rt);

            mat4_transpose(mvp);
            mat4_transpose(context.mat4_VP, mat_rt);

            // set uniforms (uniforms are same fo all vertices)
            gl.uniform1f(prog.uniforms.u_Time, ctx.time);
            gl.uniform1i(prog.uniforms.u_Mode, 0);
            gl.uniformMatrix4fv(prog.uniforms.u_VP, false, mvp);

            gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, buf_gl_elements);
            gl.drawElements(gl.TRIANGLES, k_elements, gl.UNSIGNED_SHORT, 0);
        }

        {   // render the gltf asset
                    let prog = scene.programs.objects; // use program 'objects'
                    let geom = scene.geometries.asset3;
                    let buf_gl_Position = geom.buffers_gl.a_Position;
                    let buf_gl_Normal = geom.buffers_gl.a_Normal;
                    let buf_gl_elements = geom.elements_gl;
                    let k_elements = geom.elements.length;

                    gl.useProgram(prog.id); // .id contains the WebGL identifier
                    gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl_Position);
                    gl.vertexAttribPointer(prog.attributes.a_Position, 3, gl.FLOAT, false, 0, 0);
                    gl.enableVertexAttribArray(prog.attributes.a_Position);

                    gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl_Normal);
                    gl.vertexAttribPointer(prog.attributes.a_Normal, 3, gl.FLOAT, false, 0, 0);
                    gl.enableVertexAttribArray(prog.attributes.a_Normal);

                    //create model matrix
                    let mat_t = new Float32Array(16);
                    let mat_r = new Float32Array(16);
                    let mat_rt = new Float32Array(16);
                    let mvp = new Float32Array(16);
                    mat4_set_identity(mat_t);
                    mat4_set_col(mat_t, 3, [0.0, 0.0, 3.0, 1.0]);
                    mat4_set_rotation_y(mat_r, .03 * context.time);
                    mat4_mul_mat4(mat_rt, mat_r, mat_t);
                    mat4_transpose(context.mat4_VP);
                    mat4_mul_mat4(mvp, context.mat4_VP, mat_rt);

                    mat4_transpose(mvp);
                    mat4_transpose(context.mat4_VP, mat_rt);

                    // set uniforms (uniforms are same fo all vertices)
                    gl.uniform1f(prog.uniforms.u_Time, ctx.time);
                    gl.uniform1i(prog.uniforms.u_Mode, 0);
                    gl.uniformMatrix4fv(prog.uniforms.u_VP, false, mvp);

                    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, buf_gl_elements);
                    gl.drawElements(gl.TRIANGLES, k_elements, gl.UNSIGNED_SHORT, 0);
                }
    
    {   // render the grid

        let prog = scene.programs.simple;
        let geom = scene.geometries.grid;
        let buf_gl = geom.buffers_gl["a_Position"];
        let buf = geom.buffers["a_Position"];

        gl.useProgram(prog.id);
        gl.bindBuffer(gl.ARRAY_BUFFER, buf_gl);
        gl.vertexAttribPointer(prog.attributes.a_Position, 3, gl.FLOAT, false, 0 , 0);
        gl.uniform3f(prog.uniforms.u_Color, 0.9, 0.9, 0.9);
        gl.uniformMatrix4fv(prog.uniforms.u_VP, false, context.mat4_VP);

        gl.drawArrays(gl.LINES, 0, buf.length / 3);
    }
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

    let title = document.querySelector("title");
    console.log("title is: " + title.textContent);
    loadScene(initialize)
}
