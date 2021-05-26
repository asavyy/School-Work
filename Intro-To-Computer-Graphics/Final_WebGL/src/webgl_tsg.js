// ----------------------------------------------------------------------------
// Tiny 'Scene Graph' Functionality
// ----------------------------------------------------------------------------

const PRIMITIVES = {
    POINTS : "POINTS",
    LINES : "LINES",
    TRIANGLES : "TRIANGLES"
}

const SHADER_TYPE = {
    VERTEX : "VERTEX_SHADER",
    FRAGMENT : "FRAGMENT_SHADER"
}

class Shader {
    static get TYPE() { return SHADER_TYPE; }

    constructor(type, source) {
        this.type = type;
        this.source = source;
        this.is_compiled = false;
    }
}

class Program {

    constructor(vertex_shader, fragment_shader) {
        this.vertex_shader = vertex_shader;
        this.fragment_shader = fragment_shader;
        this.is_compiled = false;
        this.attributes = { };
        this.uniforms = { };
    }

    isCompiled() { return is_compiled; }
}

class Geometry {
    static get PRIMITIVES() { return PRIMITIVES; }

    constructor(primitives) {
        this.primitives = primitives;   // primitives type
        this.buffers = { };             // JS buffer data
        this.buffers_gl = { };          // WebGL id of buffer
        this.elements = null;
        this.elements_gl = null;
    }

    addArray(attribute_name, data) {
        this.buffers[attribute_name] = data;
    }

    setElements(elements) {
        this.elements = elements;
    }
}
