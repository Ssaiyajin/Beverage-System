// Refactored initializer: use DOMContentLoaded, defensive checks and configurable defaults.
(function () {
    'use strict';

    function initSwagger() {
        try {
            if (typeof SwaggerUIBundle === 'undefined') {
                console.error('SwaggerUIBundle is not available on the page.');
                return;
            }

            const opts = window.__swaggerOptions || {};
            const url = opts.url || 'openapi.yaml';
            const domId = opts.domId || '#swagger-ui';
            const layout = opts.layout || 'StandaloneLayout';

            window.ui = SwaggerUIBundle({
                url: url,
                dom_id: domId,
                deepLinking: opts.deepLinking ?? true,
                queryConfigEnabled: opts.queryConfigEnabled ?? true,
                presets: [
                    SwaggerUIBundle.presets.apis,
                    SwaggerUIStandalonePreset
                ],
                plugins: [
                    SwaggerUIBundle.plugins.DownloadUrl
                ],
                layout: layout
            });
        } catch (err) {
            // keep failure visible during dev / production
            console.error('Failed to initialize Swagger UI:', err);
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initSwagger, { once: true });
    } else {
        initSwagger();
    }
})();
