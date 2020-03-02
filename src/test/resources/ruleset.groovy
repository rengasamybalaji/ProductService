ruleset {
  description '''
    A custom Groovy RuleSet.
    Based on http://codenarc.sourceforge.net/StarterRuleSet-AllRulesByCategory.groovy.txt
    Contains all the rules, with unapplied rules commented out.
'''

  /*
        BRACES
   */

  AssertWithinFinallyBlock
  AssignmentInConditional
  BigDecimalInstantiation
  BitwiseOperatorInConditional
  BooleanGetBoolean
  BrokenNullCheck
  BrokenOddnessCheck
  ClassForName
  ComparisonOfTwoConstants
  ComparisonWithSelf
  //ConstantAssertExpression
  ConstantIfExpression
  ConstantTernaryExpression
  DeadCode
  DoubleNegative
  DuplicateCaseStatement
  DuplicateMapKey
  DuplicateSetValue
  EmptyCatchBlock
  EmptyClass
  EmptyElseBlock
  EmptyFinallyBlock
  EmptyForStatement
  EmptyIfStatement
  EmptyInstanceInitializer
  EmptyMethod
  EmptyStaticInitializer
  EmptySwitchStatement
  EmptySynchronizedStatement
  EmptyTryBlock
  EmptyWhileStatement
  EqualsAndHashCode
  EqualsOverloaded
  ExplicitGarbageCollection
  ForLoopShouldBeWhileLoop
  HardCodedWindowsFileSeparator
  HardCodedWindowsRootDirectory
  IntegerGetInteger
  MultipleUnaryOperators
  RandomDoubleCoercedToZero
  RemoveAllOnSelf
  ReturnFromFinallyBlock
  ThrowExceptionFromFinallyBlock

  /*
        BRACES
   */

  ElseBlockBraces
  ForStatementBraces
  IfStatementBraces
  WhileStatementBraces

  /*
        CONCURRENCY
   */

  BusyWait
  DoubleCheckedLocking
  InconsistentPropertyLocking
  InconsistentPropertySynchronization
  NestedSynchronization
  StaticCalendarField
  StaticConnection
  StaticDateFormatField
  StaticMatcherField
  StaticSimpleDateFormatField
  SynchronizedMethod
  SynchronizedOnBoxedPrimitive
  SynchronizedOnGetClass
  SynchronizedOnReentrantLock
  SynchronizedOnString
  SynchronizedOnThis
  SynchronizedReadObjectMethod
  SystemRunFinalizersOnExit
  ThisReferenceEscapesConstructor
  ThreadGroup
  ThreadLocalNotStaticFinal
  ThreadYield
  UseOfNotifyMethod
  VolatileArrayField
  VolatileLongOrDoubleField
  WaitOutsideOfWhileLoop

  /*
        CONVENTION
   */

  ConfusingTernary
  //  CouldBeElvis
  //  CouldBeSwitchStatement
  FieldTypeRequired
  HashtableIsObsolete
  IfStatementCouldBeTernary
  InvertedCondition
  InvertedIfElse
  LongLiteralWithLowerCaseL
  MethodParameterTypeRequired
  //  MethodReturnTypeRequired
  //  NoDef
  NoTabCharacter
  //  ParameterReassignment
  TernaryCouldBeElvis
  //  TrailingComma
  //  VariableTypeRequired
  VectorIsObsolete

  /*
        DESIGN
   */

  AbstractClassWithPublicConstructor
  AbstractClassWithoutAbstractMethod
  AssignmentToStaticFieldFromInstanceMethod
  // BooleanMethodReturnsNull
  BuilderMethodWithSideEffects
  CloneableWithoutClone
  CloseWithoutCloseable
  CompareToWithoutComparable
  ConstantsOnlyInterface
  EmptyMethodInAbstractClass
  FinalClassWithProtectedMember
  ImplementationAsType
  Instanceof
  LocaleSetDefault
  NestedForLoop
  PrivateFieldCouldBeFinal
  PublicInstanceField
  ReturnsNullInsteadOfEmptyArray
  ReturnsNullInsteadOfEmptyCollection
  // SimpleDateFormatMissingLocale
  StatelessSingleton
  ToStringReturnsNull

  /*
        DRY
   */

  DuplicateListLiteral
  //  DuplicateMapLiteral
  // DuplicateNumberLiteral
  // DuplicateStringLiteral

  /*
        EXCEPTIONS
   */

  CatchArrayIndexOutOfBoundsException
  CatchError
  //  CatchException
  CatchIllegalMonitorStateException
  CatchIndexOutOfBoundsException
  CatchNullPointerException
  CatchRuntimeException
  CatchThrowable
  ConfusingClassNamedException
  ExceptionExtendsError
  ExceptionExtendsThrowable
  ExceptionNotThrown
  MissingNewInThrowStatement
  ReturnNullFromCatchBlock
  SwallowThreadDeath
  ThrowError
  ThrowException
  ThrowNullPointerException
  ThrowRuntimeException
  ThrowThrowable

  /*
        FORMATTING
   */

  BlankLineBeforePackage
  //  BlockEndsWithBlankLine
  //  BlockStartsWithBlankLine
  BracesForClass
  BracesForForLoop
  BracesForIfElse
  BracesForMethod
  BracesForTryCatchFinally
  //  ClassJavadoc
  //ClosureStatementOnOpeningLineOfMultipleLineClosure
  ConsecutiveBlankLines
  //  FileEndsWithoutNewline
  Indentation(spacesPerIndentLevel: 2)
  //  LineLength
  MissingBlankLineAfterImports
  MissingBlankLineAfterPackage
  SpaceAfterCatch
  SpaceAfterClosingBrace
  SpaceAfterComma
  SpaceAfterFor
  SpaceAfterIf
  SpaceAfterOpeningBrace
  SpaceAfterSemicolon
  SpaceAfterSwitch
  SpaceAfterWhile
  SpaceAroundClosureArrow
  SpaceAroundMapEntryColon(characterAfterColonRegex: /\s/, characterBeforeColonRegex: /./)
  SpaceAroundOperator
  SpaceBeforeClosingBrace
  SpaceBeforeOpeningBrace
  TrailingWhitespace

  /*
        GENERIC
   */

  IllegalClassMember
  IllegalClassReference
  IllegalPackageReference
  IllegalRegex
  IllegalString
  IllegalSubclass
  RequiredRegex
  RequiredString
  StatelessClass

  /*
        GROOVYISMS
   */

  AssignCollectionSort
  AssignCollectionUnique
  ClosureAsLastMethodParameter
  CollectAllIsDeprecated
  ConfusingMultipleReturns
  ExplicitArrayListInstantiation
  ExplicitCallToAndMethod
  ExplicitCallToCompareToMethod
  ExplicitCallToDivMethod
  ExplicitCallToEqualsMethod
  ExplicitCallToGetAtMethod
  ExplicitCallToLeftShiftMethod
  ExplicitCallToMinusMethod
  ExplicitCallToModMethod
  ExplicitCallToMultiplyMethod
  ExplicitCallToOrMethod
  ExplicitCallToPlusMethod
  ExplicitCallToPowerMethod
  ExplicitCallToRightShiftMethod
  ExplicitCallToXorMethod
  ExplicitHashMapInstantiation
  ExplicitHashSetInstantiation
  ExplicitLinkedHashMapInstantiation
  ExplicitLinkedListInstantiation
  ExplicitStackInstantiation
  ExplicitTreeSetInstantiation
  GStringAsMapKey
  //  GStringExpressionWithinString
  //  GetterMethodCouldBeProperty
  GroovyLangImmutable
  UseCollectMany
  UseCollectNested

  /*
        IMPORTS
   */

  DuplicateImport(priority: 2) // by default it is priority 3, which will not cause test failure
  ImportFromSamePackage(priority: 2)
  ImportFromSunPackages
  //  MisorderedStaticImports
  //  NoWildcardImports
  UnnecessaryGroovyImport
  UnusedImport

  /*
        JDBC
   */

  DirectConnectionManagement
  JdbcConnectionReference
  JdbcResultSetReference
  JdbcStatementReference

  /*
        JUNIT
   */

  ChainedTest
  CoupledTestCase
  JUnitAssertAlwaysFails
  JUnitAssertAlwaysSucceeds
  JUnitFailWithoutMessage
  JUnitLostTest
  JUnitPublicField
  //  JUnitPublicNonTestMethod
  //  JUnitPublicProperty
  JUnitSetUpCallsSuper
  JUnitStyleAssertions
  JUnitTearDownCallsSuper
  //  JUnitTestMethodWithoutAssert
  JUnitUnnecessarySetUp
  JUnitUnnecessaryTearDown
  JUnitUnnecessaryThrowsException
  SpockIgnoreRestUsed
  UnnecessaryFail
  UseAssertEqualsInsteadOfAssertTrue
  UseAssertFalseInsteadOfNegation
  UseAssertNullInsteadOfAssertEquals
  UseAssertSameInsteadOfAssertTrue
  UseAssertTrueInsteadOfAssertEquals
  UseAssertTrueInsteadOfNegation

  /*
        LOGGING
  */

  LoggerForDifferentClass
  LoggerWithWrongModifiers
  LoggingSwallowsStacktrace
  MultipleLoggers
  PrintStackTrace
  Println
  SystemErrPrint
  SystemOutPrint

  /*
        NAMING
  */

  AbstractClassName
  ClassName
  ClassNameSameAsFilename
  ClassNameSameAsSuperclass
  ConfusingMethodName
  //  FactoryMethodName
  FieldName
  InterfaceName
  InterfaceNameSameAsSuperInterface
  //  MethodName
  ObjectOverrideMisspelledMethodName
  //  PackageName
  PackageNameMatchesFilePath
  ParameterName
  PropertyName
  VariableName

  /*
        SECURITY
   */

  FileCreateTempFile
  InsecureRandom
  JavaIoPackageAccess
  NonFinalPublicField
  NonFinalSubclassOfSensitiveInterface
  ObjectFinalize
  PublicFinalizeMethod
  SystemExit
  UnsafeArrayDeclaration

  /*
        SERIALIZATION
   */

  EnumCustomSerializationIgnored
  SerialPersistentFields
  SerialVersionUID
  SerializableClassMustDefineSerialVersionUID

  /*
        SIZE
   */

  AbcMetric  // Requires the GMetrics jar
  ClassSize
  CrapMetric   // Requires the GMetrics jar and a Cobertura coverage file
  CyclomaticComplexity   // Requires the GMetrics jar
  MethodCount
  MethodSize
  NestedBlockDepth
  ParameterCount

  /*
        UNNECESSARY
   */

  AddEmptyString
  ConsecutiveLiteralAppends
  ConsecutiveStringConcatenation
  UnnecessaryBigDecimalInstantiation
  UnnecessaryBigIntegerInstantiation
  UnnecessaryBooleanExpression
  UnnecessaryBooleanInstantiation
  UnnecessaryCallForLastElement
  UnnecessaryCallToSubstring
  UnnecessaryCast
  UnnecessaryCatchBlock
  //  UnnecessaryCollectCall
  UnnecessaryCollectionCall
  UnnecessaryConstructor
  UnnecessaryDefInFieldDeclaration
  UnnecessaryDefInMethodDeclaration
  UnnecessaryDefInVariableDeclaration
  //  UnnecessaryDotClass
  UnnecessaryDoubleInstantiation
  //  UnnecessaryElseStatement
  UnnecessaryFinalOnPrivateMethod
  UnnecessaryFloatInstantiation
  //  UnnecessaryGString
  //  UnnecessaryGetter
  UnnecessaryIfStatement
  UnnecessaryInstanceOfCheck
  UnnecessaryInstantiationToGetClass
  UnnecessaryIntegerInstantiation
  UnnecessaryLongInstantiation
  UnnecessaryModOne
  UnnecessaryNullCheck
  UnnecessaryNullCheckBeforeInstanceOf
  //  UnnecessaryObjectReferences
  UnnecessaryOverridingMethod
  UnnecessaryPackageReference
  UnnecessaryParenthesesForMethodCallWithClosure
  UnnecessaryPublicModifier
  //  UnnecessaryReturnKeyword
  UnnecessarySafeNavigationOperator
  UnnecessarySelfAssignment
  UnnecessarySemicolon
  //  UnnecessarySetter
  UnnecessaryStringInstantiation
  UnnecessarySubstring
  UnnecessaryTernaryExpression
  //  UnnecessaryToString
  UnnecessaryTransientModifier

  /*
        UNUSED
   */

  UnusedArray
  //  UnusedMethodParameter
  UnusedObject
  UnusedPrivateField
  UnusedPrivateMethod
  UnusedPrivateMethodParameter
  //  UnusedVariable
}