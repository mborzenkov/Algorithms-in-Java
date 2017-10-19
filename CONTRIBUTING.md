# Contributing
Contributing is greatly accepted.

## Bugs
- Breaking implementations is very appreciated. If you found bug, please report it through issues or with pull request if you already fixed it. Write a simple unit test to confirm the bug if you can or at least specify an example where the algorithm breaks down.
- Same for mistakes and typos in docs - report it with issue or pull request.

## Improvements


### More algorithms
If you want some specific algorithms to be added to this project, please indicate this with new issue.
The more detailed you describe the algorithm, the greater the possibility that it will be added.

If you want to implement algorithm by yourself, please create new issue fist anyway. The algorithm will be merged to master only if it has:
- Implementation (full covered with JDoc)
- Test class (with testing strategy and tests, see any example)
- Documentation (see examples at [readme.md](./README.md))

[Example of algorithm](./docs/matrix/matrix-chain-multiplication.md) (implementation and test links at bottom)

Please be advised that all three components are required. But you can create only some (e.g. documentation + test will simplify implementation greatly).

This project contain only basic popular algorithms. It is possible that algorithm that you want is a variation of basic algorithm (e.g. problems). In this case, algorithm should be proposed to project with challenges [Challenges-in-Java](https://github.com/mborzenkov/Challenges-in-Java).
Example: Longest zig-zag sequence is variation of LIS - longest increasing sequence. LIS is for Algorithms-in-Java, Zig-zag goes to Challenges-in-Java.

### Code Style
- If you are about to open pull request, please check your code with CheckStyle and fix all warnings. [CheckStyle rules](./checkstyle.xml) for this project stored in root folder.

### Need help
The list of things that need help:
- Coding:
  - Marked as TODO in .java files (e.g. more tests in MatrixChainMultiplicationTest.java)
- Documentation
  - [Matrix multiplication](./docs/matrix/matrix-multiplication.md) - memory complexity is probably wrong for Strassen's algorithm, should improve complexity block. Also Strassen's and Recursive algorithms need to be improved to work with any matrices (not only squared and pow2).

Thank you.
