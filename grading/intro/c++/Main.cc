//
// MAIN.CC
// Main driver code for CSE 241 introductory lab.
//
// WARNING: ANY CHANGES YOU MAKE TO THIS FILE WILL BE DISCARDED
// BY THE AUTO-GRADER!  Make sure your code works with the
// unmodified original driver before you turn it in.
//

#include <cstdlib>
#include <iostream>

#include "MyThing.h"

using namespace std;

int main(int argc, char *argv[])
{
  int limit;

  // one argument: limiting value for numbers to print
  if (argc > 1)
  {
    limit = strtol(argv[1], nullptr, 10);
  }
  else
  {
    cerr << "Syntax: Main <maxValue>\n";
    exit(1);
  }

  MyThing thing;
  
  thing.count(limit);
  
  return 0;
}
