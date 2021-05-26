// ----------------------------------------------------------------------------
// GLTF resource fetching
// ----------------------------------------------------------------------------

class GLTFResource {

    static async __fetch_references__(gltf) {

        let buffers = gltf['buffers'];

        for (let i=0; i<buffers.length; ++i) {
            let response = await fetch(buffers[i]['uri']);
            let buffer = await response.arrayBuffer();
            buffers[i] = buffer;
            Log.info(`referenced buffers[${i}] loaded.`)
        }
    }

    static async fetch(filename) {

        let gltf;
        try {
            let response = await fetch(filename);
            console.log(response)
            if (!response.ok) {
                throw Error(response.statusText);
            }
            gltf = await response.json();
            await this.__fetch_references__(gltf);
            console.log(gltf);
        } catch (e) {
            Log.error(`can't fetch ${filename} (${e})`)
            gltf = undefined;
        }
        return gltf;
    }
}





