# Git Commit Message Instructions

Please follow these guidelines when generating commit messages.

- Start the subject line with a type (one of):
  - `feat`, `fix`, `chore`, `docs`, `style`, `refactor`, `perf`, `test`, `build`, `ci`
- Optionally include a scope: `type(scope): subject`
- Subject rules:
  - Max 50 characters.
  - Use imperative mood (e.g., `Add`, `Update`, not `Added`, `Updated`).
  - Capture the essence of the change concisely.

- Body (separate from subject with a blank line):
  - Use bullet points for detailed changes.
  - Each bullet should start with a verb (e.g., `Add`, `Improve`, `Remove`).
  - Mention specific classes, methods, or modules when relevant using backticks (e.g., `UserService`, `processOrder()`).
  - Explain *why* the change was made and any important details (not a line-by-line log).
  - Reference related issues or tasks using `#123` or `PROJECT-456`.

- Footer (when needed):
  - For breaking changes: start with `BREAKING CHANGE:` followed by a short explanation.
  - For co-authored commits: use `Co-authored-by: Name <email@example.com>`.
  - For merges or automated updates, keep the message descriptive and follow the same rules.

Examples:
- `feat(auth): add JWT token refresh endpoint`
  - Add `RefreshTokenController`.
  - Store refresh tokens in `RefreshTokenRepository`.
  - Fixes `#42`.

- `fix(order): handle null items in cart`
  - Prevent NPE in `CartService.calculateTotal()`.
  - Add unit tests for null and empty cart cases.

- `docs: update README setup instructions`
  - Clarify Java and Maven versions.
  - Add example `application.properties` values.

Keep messages consistent and focused — subject for the what, body for the why and details.