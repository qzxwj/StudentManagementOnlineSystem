# Git Version Workflow

This project uses a lightweight single-developer Git workflow.

## Branches

- `main`: stable, runnable versions only.
- `feature/<name>`: new feature development.
- `fix/<name>`: bug fixes.

Start new work from an up-to-date `main`:

```bash
git checkout main
git pull origin main
git checkout -b feature/course-selection
```

Merge completed work back to `main`:

```bash
git checkout main
git pull origin main
git merge feature/course-selection
git push origin main
```

## Version Tags

Use annotated tags for stable versions:

```bash
git tag -a v1.0.0 -m "First stable version"
git push origin v1.0.0
```

Suggested version rules:

- `v1.0.0`: first stable version.
- `v1.1.0`, `v1.2.0`: feature iterations.
- `v1.0.1`, `v1.0.2`: bug fixes.

## Rollback

Inspect available versions:

```bash
git tag
git log --oneline --decorate --graph --all
```

Temporarily view an old version:

```bash
git checkout v1.0.0
```

Create a fix branch from an old version:

```bash
git checkout -b fix/from-v1.0.0 v1.0.0
```

Safely undo a commit on `main`:

```bash
git checkout main
git revert <commit>
git push origin main
```

Avoid rewriting shared history unless you intentionally want to replace the remote history.

## Submodules

This repository keeps `awesome-design-md` as a Git submodule.

Clone with submodules:

```bash
git clone --recurse-submodules git@github.com:qzxwj/StudentManagementOnlineSystem.git
```

Or initialize submodules after cloning:

```bash
git submodule update --init --recursive
```

## Pre-release Checklist

- Backend starts successfully.
- Frontend starts or builds successfully.
- Core login, student, teacher, and course flows work.
- `git status` contains no accidental `.DS_Store`, `dist/`, `target/`, `.idea/`, or `node_modules/` changes.
- `git submodule status` runs without `.gitmodules` mapping errors.
