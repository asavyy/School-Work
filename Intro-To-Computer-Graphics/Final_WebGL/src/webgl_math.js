

function mat4_set_identity(mat4) {
    
    mat4.set([
        1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0
    ]);
}

function mat4_set_rotation_y(mat4, angle) {
    let cosA = Math.cos(angle);
    let sinA = Math.sin(angle);

    mat4.set([
        cosA, 0.0, -sinA, 0.0,
        0.0,  1.0,   0.0, 0.0,
        sinA, 0.0,  cosA, 0.0,
        0.0,  0.0,   0.0, 1.0
    ]);
}

function mat4_set_rotation_rand(mat4, angle) {
    let cosA = Math.cos(angle);
    let sinA = Math.sin(angle);

    mat4.set([
        cosA, sinA, cosA, 0.0,
        -sinA,  cosA,   sinA, 0.0,
        0.0, 0.0,  0.0, 0.0,
        0.0,  0.0,   0.0, 1.0
    ]);
}

function mat4_set_rotation_z(mat4, angle) {
    let cosA = Math.cos(angle);
    let sinA = Math.sin(angle);

    mat4.set([
        cosA, -sinA, 0.0, 0.0,
        sinA, cosA, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0,  0.0,   0.0, 1.0
    ]);
}

function mat4_set_rotation_x(mat4, angle) {
    let cosA = Math.cos(angle);
    let sinA = Math.sin(angle);

    mat4.set([
        1, 0.0, 0.0, 0.0,
        0.0, cosA, -sinA, 0.0,
        0.0, sinA, cosA, 0.0,
        0.0,  0.0,   0.0, 1.0
    ]);
}


function mat4_set_rotation_y(mat4, angle) {
    let cosA = Math.cos(angle);
    let sinA = Math.sin(angle);

    mat4.set([
        cosA, 0.0, -sinA, 0.0,
        0.0,  1.0,   0.0, 0.0,
        sinA, 0.0,  cosA, 0.0,
        0.0,  0.0,   0.0, 1.0
    ]);
}

function mat4_transpose(mat4) {

    [ mat4[ 1], mat4[ 4] ] = [ mat4[ 4], mat4[ 1] ];
    [ mat4[ 2], mat4[ 8] ] = [ mat4[ 8], mat4[ 2] ];
    [ mat4[ 3], mat4[12] ] = [ mat4[12], mat4[ 3] ];

    [ mat4[ 6], mat4[ 9] ] = [ mat4[ 9], mat4[ 6] ];
    [ mat4[ 7], mat4[13] ] = [ mat4[13], mat4[ 7] ];    

    [ mat4[11], mat4[14] ] = [ mat4[14], mat4[11] ];

}

function mat4_set_col(mat4, j, col) {
    mat4[j+ 0] = col[0];
    mat4[j+ 4] = col[1];
    mat4[j+ 8] = col[2];
    mat4[j+12] = col[3];
}

function mat4_set_row(mat4, i, row) {
    mat4[4*i+0] = row[0];
    mat4[4*i+1] = row[1];
    mat4[4*i+2] = row[2];
    mat4[4*i+3] = row[3];
}

function mat4_mul_mat4(res4, A4, B4) {

    res4[ 0] = A4[ 0]*B4[ 0] + A4[ 1]*B4[ 4] + A4[ 2]*B4[ 8] + A4[ 3]*B4[12];
    res4[ 4] = A4[ 4]*B4[ 0] + A4[ 5]*B4[ 4] + A4[ 6]*B4[ 8] + A4[ 7]*B4[12];
    res4[ 8] = A4[ 8]*B4[ 0] + A4[ 9]*B4[ 4] + A4[10]*B4[ 8] + A4[11]*B4[12];
    res4[12] = A4[12]*B4[ 0] + A4[13]*B4[ 4] + A4[14]*B4[ 8] + A4[15]*B4[12];

    res4[ 1] = A4[ 0]*B4[ 1] + A4[ 1]*B4[ 5] + A4[ 2]*B4[ 9] + A4[ 3]*B4[13];
    res4[ 5] = A4[ 4]*B4[ 1] + A4[ 5]*B4[ 5] + A4[ 6]*B4[ 9] + A4[ 7]*B4[13];
    res4[ 9] = A4[ 8]*B4[ 1] + A4[ 9]*B4[ 5] + A4[10]*B4[ 9] + A4[11]*B4[13];
    res4[13] = A4[12]*B4[ 1] + A4[13]*B4[ 5] + A4[14]*B4[ 9] + A4[15]*B4[13];

    res4[ 2] = A4[ 0]*B4[ 2] + A4[ 1]*B4[ 6] + A4[ 2]*B4[10] + A4[ 3]*B4[14];
    res4[ 6] = A4[ 4]*B4[ 2] + A4[ 5]*B4[ 6] + A4[ 6]*B4[10] + A4[ 7]*B4[14];
    res4[10] = A4[ 8]*B4[ 2] + A4[ 9]*B4[ 6] + A4[10]*B4[10] + A4[11]*B4[14];
    res4[14] = A4[12]*B4[ 2] + A4[13]*B4[ 6] + A4[14]*B4[10] + A4[15]*B4[14];

    res4[ 3] = A4[ 0]*B4[ 3] + A4[ 1]*B4[ 7] + A4[ 2]*B4[11] + A4[ 3]*B4[15];
    res4[ 7] = A4[ 4]*B4[ 3] + A4[ 5]*B4[ 7] + A4[ 6]*B4[11] + A4[ 7]*B4[15];
    res4[11] = A4[ 8]*B4[ 3] + A4[ 9]*B4[ 7] + A4[10]*B4[11] + A4[11]*B4[15];
    res4[15] = A4[12]*B4[ 3] + A4[13]*B4[ 7] + A4[14]*B4[11] + A4[15]*B4[15];

}

function mat4_set_orthogonal(mat4, left, right, bottom, top, near, far) {
    let irl = 1.0 / (right-left);
    let itb = 1.0 / (top-bottom);
    let ifn = 1.0 / (far-near);
    mat4.set([
        2.0*irl, 0.0, 0.0, (right+left)*irl,
        0.0, 2.0*itb, 0.0, (top+bottom)*itb,
        0.0, 0.0, 2.0*ifn, (far+near)*ifn,
        0.0, 0.0, 0.0, 1.0
    ]);
}

function mat4_set_perspective(mat4, fov, aspect, near, far) {
    let tfd2 = Math.tan(fov*0.5);
    let fmn = 1.0 / (near - far);
    
    mat4.set([
        1.0 / (aspect * tfd2), 0.0, 0.0, 0.0,
        0.0, 1.0 / (tfd2), 0.0, 0.0,
        0.0, 0.0, fmn*(far+near), fmn*(2.0*far*near),
        0.0, 0.0, -1.0, 0.0
    ]);
}

function arr3_normalize(arr3) {
    let factor = 1.0 / Math.sqrt(arr3[0]*arr3[0] + arr3[1]*arr3[1] + arr3[2]*arr3[2]);
    arr3[0] *= factor;
    arr3[1] *= factor;
    arr3[2] *= factor;
}

// cross product: 
//   - produces a vector that is orthogonal to vA and vB
//   - norm(cross(vA,vB))==1 if norm(vA) == norm(vB) == 1
//   - entries come from wedge product in 3D
function arr3_cross(cross, vA, vB) {
    cross[0] = vA[1]*vB[2] - vA[2]*vB[1];
    cross[1] = vA[2]*vB[0] - vA[0]*vB[2];
    cross[2] = vA[0]*vB[1] - vA[1]*vB[0];
}

function mat4_set_lookat(mat4, eye, center, up) {
    let forward = [center[0] - eye[0], center[1] - eye[1], center[2] - eye[2]];
    let side = [0,0,0];
    
    arr3_normalize(forward);
    arr3_normalize(up);
    arr3_cross(side, forward, up);
    arr3_normalize(side);
    arr3_cross(up, side, forward);
    
    mat4[ 0] =     side[0]; mat4[ 1] =     side[1]; mat4[ 2] =     side[2];
    mat4[ 4] =       up[0]; mat4[ 5] =       up[1]; mat4[ 6] =       up[2];
    mat4[ 8] = -forward[0]; mat4[ 9] = -forward[1]; mat4[10] = -forward[2]; 
    
    mat4[ 3] = -    side[0]*eye[0] -    side[1]*eye[1] -    side[2]*eye[2];
    mat4[ 7] = -      up[0]*eye[0] -      up[1]*eye[1] -      up[2]*eye[2];
    mat4[11] = + forward[0]*eye[0] + forward[1]*eye[1] + forward[2]*eye[2];

    mat4[12] = 0.0;   mat4[13] = 0.0; mat4[14] = 0.0; mat4[15] = 1.0;
}