# File Structure (focused on auth only)

```
src/
  app/
    app.config.ts
    app.routes.ts

    core/                         # Cross-cutting, app-wide singletons
      http/
        api-client.ts             # Optional wrapper around HttpClient
      interceptors/
        auth-interceptor.ts       # Adds Authorization: Bearer <token> [web:427]
        error-interceptor.ts      # Maps 401/403 -> logout, toast, redirect [web:427]
      guards/
        auth-guard.ts             # Protect routes
      config/
        environment.tokens.ts     # Injection tokens, config, base URL
      layout/
        shell/
          shell.component.ts      # Navbar/sidebar layout
      utils/
        storage.ts                # localStorage/sessionStorage helpers

    shared/                       # Dumb reusable UI + pipes/directives
      ui/
        button/
        input/
      pipes/
      directives/

    features/
      auth/
        pages/                    # Routed components only
          login/
            login.page.ts
            login.page.html
          register/
            register.page.ts
            register.page.html
        data-access/              # API calls + token storage
          auth-api.ts             # calls /auth/login, /auth/register
          auth-store.ts           # signal/store for auth state (optional)
          token.service.ts        # get/set/clear token
        models/
          auth-request.ts
          auth-response.ts
          reporter.ts
        auth.routes.ts            # routes for auth pages [web:423]

      ingestion/                  # Example feature consuming secured endpoints
        pages/
        data-access/
        models/
        ingestion.routes.ts
```