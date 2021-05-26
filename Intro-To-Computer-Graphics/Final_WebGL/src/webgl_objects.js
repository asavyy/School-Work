// ---------------------------------------------------------------------------- 
// Shaders
// ----------------------------------------------------------------------------

let VSHADER_OBJECTS =
`
    attribute vec3 a_Position;
    attribute vec3 a_Normal;
    uniform mat4 u_VP;
    uniform float u_Time;
    uniform int u_Mode;

    varying vec4 v_Color;

    void main() {
        float time = u_Time;
        const float speedA = 0.2 * (2.0 * 3.1415);
        const float speedB = 0.1 * (2.0 * 3.1415);
        const float speedL = 0.1 * (2.0 * 3.1415);
        vec3 light = vec3(3.0, 0.0, 0.0);

        float angleL = speedL * time;
        float cosL = cos(angleL);
        float sinL = sin(angleL);

        float a = speedA * time;
        float b = speedB * time;
        // a = 0.5;
        // b = 0.8;
        float sinA = sin(a);
        float cosA = cos(a);
        mat4 T1 = mat4(
           1.0,   0.0, 0.0,   0.0,
           0.0,   1.0, 0.0,   0.0,
           0.0,   0.0, 1.0,  0.0,
           0.0,   0.0, 0.0,   1.0      
        );
        mat4 R2 = mat4(
           cos(b),   0.0, sin(b), 0.0,
              0.0,   1.0, 0.0,    0.0,
          -sin(b),   0.0, cos(b), 0.0,
              0.0,   0.0, 0.0,    1.0
        );
        mat4 R1 = mat4(
           cosA, sinA, 0.0, 0.0,
          -sinA, cosA, 0.0, 0.0,
            0.0,  0.0, 1.0, 0.0,
            0.0,  0.0, 0.0, 1.0
        );
        mat4 MV = mat4(
          1.0, 0, 0,     0,
          0, 1.0, 0,     0,
          0, 0, 1.0,     0,
          -0.5, -0.5, -0.5, 1
        );

        // gl_Position = u_VP*T1*R2*R1*MV*vec4(a_Position.xyz, 1.0);
        gl_Position = u_VP*vec4(a_Position.xyz, 1.0);
        // gl_Position = R1*MV*vec4(a_Position.xyz, 1.0);

        // lighting calculations 
        mat3 RL = mat3(
           cosL, 0.0, sinL,
            0.0, 1.0,  0.0,
          -sinL, 0.0, cosL
        );
        light = normalize(RL*light);

        vec3 normal = normalize(a_Normal);
        float nDotL = max(dot(normal, light), 0.0);
        vec3 color = vec3(0.0, 0.5, 1.0);

        if (u_Mode == 0) {
          float ambient = 0.2;
          color = ambient * color + (1.0-ambient) * nDotL * color; 
          // color = ambient * color;
          v_Color = vec4(color, 1.0);
        } else {
          v_Color = vec4(color, 1.0);
        }
    }
`

let FSHADER_OBJECTS =
`
    precision highp float;
    varying vec4 v_Color;
    void main() {
        gl_FragColor = v_Color;
    }
`

let VSHADER_SIMPLE = 
`
    attribute vec3 a_Position;

    uniform mat4 u_VP;
    uniform vec3 u_Color;

    varying vec4 v_Color;

    void main() {
        gl_Position = u_VP*vec4(a_Position, 1.0);
        v_Color = vec4(u_Color, 1.0);
    }
`

let FSHADER_SIMPLE =
`
    precision highp float;

    varying vec4 v_Color;
    
    void main() {
        gl_FragColor = v_Color;
    }
`

// ---------------------------------------------------------------------------- 
// Geometry of Tiny House in 3D, 
//   x,z is ground plane, y is height
// ----------------------------------------------------------------------------
let irt = 1.0 / Math.sqrt(2.0); // irrational 

let tinyHouse = {
    vertices : [
        0.0,0.0,0.0,  1.0,0.0,0.0,  1.0,0.0,1.0,  0.0,0.0,1.0, // base
        1.0,0.0,0.0,  1.0,1.0,0.0,  1.0,1.0,1.0,  1.0,0.0,1.0, // right side
        0.0,0.0,0.0,  0.0,0.0,1.0,  0.0,1.0,1.0,  0.0,1.0,0.0, // left side
        1.0,1.0,0.0,  0.5,1.5,0.0,  0.5,1.5,1.0,  1.0,1.0,1.0, // right roof
        0.0,1.0,0.0,  0.0,1.0,1.0,  0.5,1.5,1.0,  0.5,1.5,0.0, // left roof
        0.0,0.0,1.0,  1.0,0.0,1.0,  1.0,1.0,1.0,  0.5,1.5,1.0, 0.0,1.0,1.0, // front
        0.0,0.0,0.0,  0.0,1.0,0.0,  0.5,1.5,0.0,  1.0,1.0,0.0, 1.0,0.0,0.0, // back
    ],
    normals : [
        0.0,-1.0,0.0, 0.0,-1.0,0.0, 0.0,-1.0,0.0, 0.0,-1.0,0.0, // base
        +1.0,0.0,0.0, +1.0,0.0,0.0, +1.0,0.0,0.0, +1.0,0.0,0.0, // right side
        -1.0,0.0,0.0, -1.0,0.0,0.0, -1.0,0.0,0.0, -1.0,0.0,0.0, // left side
        +irt,irt,0.0, +irt,irt,0.0, +irt,irt,0.0, +irt,irt,0.0, // right roof
        -irt,irt,0.0, -irt,irt,0.0, -irt,irt,0.0, -irt,irt,0.0, // left roof
        0.0,0.0,+1.0, 0.0,0.0,+1.0,  0.0,0.0,+1.0,  0.0,0.0,+1.0, 0.0,0.0,+1.0, // front
        0.0,0.0,-1.0, 0.0,0.0,-1.0,  0.0,0.0,-1.0,  0.0,0.0,-1.0, 0.0,0.0,-1.0, // back 
    ],
    indices_triangles : [
         0,  1,  2,    0,  2,  3,              // base
         4,  5,  6,    4,  6,  7,              // right side
         8,  9, 10,    8, 10, 11,              // left side
        12, 13, 14,   12, 14, 15,              // right roof
        16, 17, 18,   16, 18, 19,              // left roof
        20, 21, 22,   20, 22, 24,  22, 23, 24, // front 
        25, 26, 27,   27, 28, 29,  25, 27, 29, // back 
    ],
    indices_outlines : [
          0, 1,   1, 2,   2, 3,   3, 0,         // base
          4, 5,   5, 6,   6, 7,   7, 4,         // right side
          8, 9,   9,10,  10,11,  11, 8,         // left side
         12,13,  13,14,  14,15,  15,12,         // right roof
         16,17,  17,18,  18,19,  19,16,         // left roof
         20,21,  21,22,  22,23,  23,24,  24,20, // front
         25,26,  26,27,  27,28,  28,29,  29,25  // back
    ]
}

// ---------------------------------------------------------------------------- 
// Geometry for Tower
// ----------------------------------------------------------------------------
//
function create_tower() {
    let n_sides = 16;   // at least 3
    let y_profile = [ // x (radius), y (height)
        [0.0, 0.0],
        [0.6, 0.0],
        [0.6, 0.0],
        [0.6, 1.8],
        [0.6, 1.8],
        [0.4, 2.0],
        [0.4, 2.0],
        [0.4, 2.3],        
        [0.4, 2.3],
        [0.0, 2.7]
    ];
    let y_normals = [ // along profile
        [0.0, -1.0],
        [0.0, -1.0],
        [1.0,  0.0],
        [1.0,  0.0],  
        [1.0,  1.0],
        [1.0,  1.0],  
        [1.0,  0.0],        
        [1.0,  0.0],  
        [1.0,  1.0],
        [1.0,  1.0],
    ];
    let n_rings = y_profile.length; // at least 4
    
    let n_vertices = n_rings*n_sides;

    let tower = {
        vxs : new Float32Array(3 * n_vertices), // vertices
        nms  : new Float32Array(3 * n_vertices), // normals
        idxs : new Uint16Array((3*n_rings-1)*n_sides), // triangle idxs
        idx_rings : new Uint16Array(8*n_sides + 6*n_sides + 6*n_sides)
    };
    let t = tower;

    let angle_part = (Math.PI * 2.0) / n_sides;
    let n_sidesX3 = 3*n_sides;
    let n_sidesX6 = 6*n_sides;
    
    // loop over all vertices in rings
    for (let i=0; i<n_sides; ++i) {
        let angle = i*angle_part;
        let [cosA, sinA] = [Math.cos(angle), Math.sin(angle)];

        // loop over rings
        let u = 3*i; // idx_vertex in array
        for (let k=0; k<n_rings; ++k) {
            let [r, h] = [y_profile[k][0], y_profile[k][1]];
            let [nx, ny] = [y_normals[k][0], y_normals[k][1]];
            let [vxA, vyA, vzA] = [r*cosA, h, r*sinA];
            let [nxA, nyA, nzA] = [nx*cosA, ny, nx*sinA];
            t.vxs[u+0] = vxA; t.vxs[u+1] = vyA; t.vxs[u+2] = vzA;
            t.nms[u+0] = nxA; t.nms[u+1] = nyA; t.nms[u+2] = nzA;
            u += n_sidesX3; // goto next higher ring
        }

        // bottom and top faces
        let iX3 = i*3;
        let iP1 = (i+1) % n_sides;
        t.idxs[iX3+0] = i; // ring 1 vertex i+1
        t.idxs[iX3+1] = n_sides + i; // ring 1 vertex i
        t.idxs[iX3+2] = n_sides + iP1; // ring 0 vertex i
        t.idxs[n_sidesX3+iX3+0] = (n_rings-2)*n_sides + iP1; // R n-2 V i+1
        t.idxs[n_sidesX3+iX3+1] = (n_rings-2)*n_sides + i; // R n-2 V i
        t.idxs[n_sidesX3+iX3+2] = (n_rings-1)*n_sides; // R n-1 V i

        // remaining faces
        let iX6 = i*6;
        for (let k=2; k<n_rings-2; k+=2) {
            let idx0 = k*n_sides + i;
            let idx1 = (k+1)*n_sides + i;
            let idx2 = (k+1)*n_sides + iP1;
            let idx3 = k*n_sides + iP1;

            t.idxs[k*n_sidesX3+iX6+0] = idx0;
            t.idxs[k*n_sidesX3+iX6+1] = idx1;
            t.idxs[k*n_sidesX3+iX6+2] = idx2;
            t.idxs[k*n_sidesX3+iX6+3] = idx0;
            t.idxs[k*n_sidesX3+iX6+4] = idx2;
            t.idxs[k*n_sidesX3+iX6+5] = idx3;
        }
    }

    console.log("n_rings=" + n_rings + " n_sides=" + n_sides);
    console.log("n_vertices=" + n_vertices + " [" + (4*3*n_vertices) + "bytes]");
    console.log("n_idxs = " + t.idxs.length);

    return tower;
}


// ---------------------------------------------------------------------------- 
// Geometry for Grid
// ----------------------------------------------------------------------------
//
function create_grid_vertices() {

    let grid = {
        bounds : {
          a : { lower : -5, upper : 5 },
          b : { lower : -5, upper : 5 }
        },
    spacing : {
          a : 1.0,
          b : 1.0,
        }
    }

    let aL = Math.trunc(grid.bounds.a.lower / grid.spacing.a);
    let aH = Math.trunc(grid.bounds.a.upper / grid.spacing.a);
    let bL = Math.trunc(grid.bounds.b.lower / grid.spacing.b);
    let bH = Math.trunc(grid.bounds.b.upper / grid.spacing.b);

    if (aL>aH) { [aL,aH] = [aH,aL]; }
    if (bL>bH) { [bL,bH] = [bH,bL]; }
    let nA = aH - aL + 1;
    let nB = bH - bL + 1;

    let vs = new Float32Array(6*(nA+nB));

    let k = 0;
    for (let i=aL; i<=aH; ++i) {
        vs[k++] = i*grid.spacing.a;
        vs[k++] = 0.0;
        vs[k++] = grid.bounds.b.lower;
        vs[k++] = i*grid.spacing.a;
        vs[k++] = 0.0;
        vs[k++] = grid.bounds.b.upper;
    }
    for (let j=bL; j<=bH; ++j) {
        vs[k++] = grid.bounds.a.lower;
        vs[k++] = 0.0;
        vs[k++] = j*grid.spacing.b;
        vs[k++] = grid.bounds.a.upper;
        vs[k++] = 0.0;
        vs[k++] = j*grid.spacing.b;    
    }

    grid.vertices = vs;
    this.grid = grid;

    return vs;
}

// ----------------------------------------------------------------------------
// Geometry of GLTF object
// ----------------------------------------------------------------------------
// see: https://github.com/KhronosGroup/glTF-Tutorials
// note: you may want to modify this code to support multiple meshes
function create_asset_from_gltf(gltf) {

    console.log(gltf);
    console.log(`[info] GLTF has ${gltf['scenes'].length} scenes.`);
    let scene0 = gltf['scenes'][0];
    console.log(`[info] GLTF scene[0] has ${scene0['nodes'].length} nodes.`);
    let node0 = gltf['nodes'][0];
    console.log(`[info] GLTF node[0] has name '${node0['name']}'.`);
    let mesh0 = gltf['meshes'][0];
    console.log(`[info] GLTF mesh[0] has name '${mesh0['name']}'.`);
    let primitives0 = mesh0['primitives'][0];
    let POSITION = primitives0['attributes']['POSITION'];
    let NORMAL = primitives0['attributes']['NORMAL'];
    let INDICES = primitives0['indices'];
    let accessor_POSITION = gltf['accessors'][POSITION];
    let accessor_NORMAL = gltf['accessors'][NORMAL];
    let accessor_INDICES = gltf['accessors'][INDICES];
    console.log(accessor_POSITION);
    console.log(accessor_NORMAL);
    console.log(accessor_INDICES);
    let bufview_POSITION = gltf['bufferViews'][accessor_POSITION['bufferView']];
    let bufview_NORMAL = gltf['bufferViews'][accessor_NORMAL['bufferView']];
    let bufview_INDICES = gltf['bufferViews'][accessor_INDICES['bufferView']];
    console.log(bufview_POSITION);
    console.log(bufview_NORMAL);
    console.log(bufview_INDICES);
    let buf_POSITION = gltf['buffers'][bufview_POSITION['buffer']];
    let buf_NORMAL = gltf['buffers'][bufview_NORMAL['buffer']];
    let buf_INDICES = gltf['buffers'][bufview_INDICES['buffer']];

    let vertices = new Float32Array(
        buf_POSITION, bufview_POSITION['byteOffset'], bufview_POSITION['byteLength']/4);
    let normals = new Float32Array(
        buf_NORMAL, bufview_NORMAL['byteOffset'], bufview_NORMAL['byteLength']/4);
    let indices = new Uint16Array(
        buf_INDICES, bufview_INDICES['byteOffset'], bufview_INDICES['byteLength']/2);

    return {
        vertices: vertices,
        normals: normals,
        indices: indices
    };
}


class Scene {

    constructor() {
    }

    async load(cb_initialized) {

        // shader programs
        this.programs = {
            objects : new Program(
                new Shader(Shader.TYPE.VERTEX, VSHADER_OBJECTS),
                new Shader(Shader.TYPE.FRAGMENT, FSHADER_OBJECTS),
                ),
            simple : new Program(
                new Shader(Shader.TYPE.VERTEX, VSHADER_SIMPLE),
                new Shader(Shader.TYPE.FRAGMENT, FSHADER_SIMPLE),
                ) 
        };

        // create grid
        let grid = new Geometry(Geometry.PRIMITIVES.LINES);
        grid.addArray("a_Position", create_grid_vertices());

        // create tiny house
        let house = new Geometry(Geometry.PRIMITIVES.TRIANGLES);
        house.addArray("a_Position", new Float32Array(tinyHouse.vertices));
        house.addArray("a_Normal", new Float32Array(tinyHouse.normals));
        house.setElements(new Uint8Array(tinyHouse.indices_triangles));

        // create wireframe model of house
        let house_wires = new Geometry(Geometry.PRIMITIVES.LINES);
        house_wires.addArray("a_Position", new Float32Array(tinyHouse.vertices));
        house_wires.addArray("a_Normal", new Float32Array(tinyHouse.normals));
        house_wires.setElements(new Uint8Array(tinyHouse.indices_outlines));

        // create tower model
        let tower = create_tower();
        let tower_points = new Geometry(Geometry.PRIMITIVES.POINTS);
        tower_points.addArray("a_Position", new Float32Array(tower.vxs));
        tower_points.addArray("a_Normal", new Float32Array(tower.nms));
        tower_points.setElements(new Uint16Array(tower.idxs));

        // add objects
        this.geometries = {
            grid : grid,
            house : house,
            house_wires : house_wires,
            tower : tower_points,
        };

        // load model
        //let gltf = await GLTFResource.fetch("cube_mod1.gltf");
        let gltf = await GLTFResource.fetch("asteroid.gltf");
        if (gltf) {
            let asset = create_asset_from_gltf(gltf, cb_initialized);
            let asset_triangles = new Geometry(Geometry.PRIMITIVES.TRIANGLES);
            asset_triangles.addArray('a_Position', asset.vertices);
            asset_triangles.addArray('a_Normal', asset.normals);
            asset_triangles.setElements(asset.indices);
            this.geometries.asset = asset_triangles;
        }

       let gltf2 = await GLTFResource.fetch("planet.gltf");
        if (gltf2) {
             let asset2 = create_asset_from_gltf(gltf2, cb_initialized);
             let asset_triangles2 = new Geometry(Geometry.PRIMITIVES.TRIANGLES);
             asset_triangles2.addArray('a_Position', asset2.vertices);
             asset_triangles2.addArray('a_Normal', asset2.normals);
             asset_triangles2.setElements(asset2.indices);
             this.geometries.asset2 = asset_triangles2;
       }

       let gltf3 = await GLTFResource.fetch("ship.gltf");
       if (gltf3) {
            let asset3 = create_asset_from_gltf(gltf3, cb_initialized);
            let asset_triangles3 = new Geometry(Geometry.PRIMITIVES.TRIANGLES);
            asset_triangles3.addArray('a_Position', asset3.vertices);
            asset_triangles3.addArray('a_Normal', asset3.normals);
            asset_triangles3.setElements(asset3.indices);
            this.geometries.asset3 = asset_triangles3;
      }
      }
}


function loadScene(cb_initialized) {
    console.log('[info] start loading scene data');
    let scene = new Scene();
    scene.load(cb_initialized)
        .then(() => cb_initialized(scene)) // register callback
        .catch(() => console.log('[ERROR] cannot load scene data'));
}