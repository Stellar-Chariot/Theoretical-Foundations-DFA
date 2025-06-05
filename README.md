# Theoretical-Foundations-DFA
Class Group Project

# Collaborators

- James Traylor
- Thi Tran
- Malcolm Gayle

---

# Theoretical Foundations — DFA Simulator

This project is a Deterministic Finite Automaton (DFA) simulator created for a group assignment in our Theoretical Foundations course. It reads a DFA definition from a text file and evaluates user-input strings to determine if they are accepted or rejected by the automaton.

---

## Project Structure
```
TheoreticalFoundation/
  ├── DFA.java # Main DFA simulator source code
  └── dfa_description.txt # Sample DFA configuration file
```
---

## DFA Description File Format

The simulator expects a DFA configuration file in the following format:
```
{a,b} ← Input alphabet
{q0,q1,q2} ← States
q0 ← Start state
{q2} ← Accept states
(q0,a) -> q1 ← Transitions
(q1,b) -> q2
(q2,a) -> q2
```
---

## How to Run

### Option 1: With File as Argument
```bash
java TheoreticalFoundation.DFA path/to/dfa_description.txt
