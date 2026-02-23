# JDC++ Compiler ⚙️

A complete compiler for JDC++, a custom programming language inspired by C++ and Java. This project translates high-level source code into executable WebAssembly (WASM) through a custom-built lexical, syntactic, and semantic pipeline.

## Architecture & Technologies

* **Lexical Analysis (JFlex):** Tokenization of custom syntax and regex-based pattern matching for language-specific keywords and literals.
* **Syntactic Analysis (CUP):** Context-Free Grammar implementation. Resolved arithmetic ambiguity through a manually stratified grammar (E0 to E8 levels) and embedded Java actions for real-time Abstract Syntax Tree (AST) construction.
* **Semantic Engine (Java):** Custom two-pass AST traversal.
  * **Pass 1 (Binding):** Scope and shadowing management using a Dynamic Symbol Table (Stack of HashMaps).
  * **Pass 2 (Typing):** Strong typing enforcement, strict function signature matching, and memory access validation.
* **Code Generation (WASM):** Stack Machine design pattern to recursively generate portable WebAssembly instructions.

## Key Technical Highlights

* **N-Dimensional Array Validation:** Implemented a recursive semantic algorithm to calculate access depth at compile time. It dynamically resolves exact index matches, boundary errors, and partial accesses (returning new sub-array types).
* **Robust Memory & Scope Management:** Handled complex logic for pointers, indirection levels, and nested execution contexts.
