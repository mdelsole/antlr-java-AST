// Generated from pascal.g4 by ANTLR 4.7.2

    import java.lang.Math;
    import java.util.HashMap;
    import java.util.Scanner;
    import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pascalParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pascalVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pascalParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(pascalParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(pascalParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#variableBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableBlock(pascalParser.VariableBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varInitialization}
	 * labeled alternative in {@link pascalParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarInitialization(pascalParser.VarInitializationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDeclaration}
	 * labeled alternative in {@link pascalParser#varDec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(pascalParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#varValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarValue(pascalParser.VarValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#varNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarNameList(pascalParser.VarNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#programBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramBlock(pascalParser.ProgramBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#statementList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementList(pascalParser.StatementListContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(pascalParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(pascalParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithSpclExpr}
	 * labeled alternative in {@link pascalParser#mathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithSpclExpr(pascalParser.ArithSpclExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithElement}
	 * labeled alternative in {@link pascalParser#mathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithElement(pascalParser.ArithElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithExpr}
	 * labeled alternative in {@link pascalParser#mathExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithExpr(pascalParser.ArithExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithExprElement}
	 * labeled alternative in {@link pascalParser#mathElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithExprElement(pascalParser.ArithExprElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithValueElement}
	 * labeled alternative in {@link pascalParser#mathElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithValueElement(pascalParser.ArithValueElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arithVarElement}
	 * labeled alternative in {@link pascalParser#mathElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithVarElement(pascalParser.ArithVarElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolElement}
	 * labeled alternative in {@link pascalParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolElement(pascalParser.BoolElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link pascalParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(pascalParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolNotExpr}
	 * labeled alternative in {@link pascalParser#logicExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolNotExpr(pascalParser.BoolNotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolExprElement}
	 * labeled alternative in {@link pascalParser#logicElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExprElement(pascalParser.BoolExprElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolValueElement}
	 * labeled alternative in {@link pascalParser#logicElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolValueElement(pascalParser.BoolValueElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolVarElement}
	 * labeled alternative in {@link pascalParser#logicElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolVarElement(pascalParser.BoolVarElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#ifBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfBlock(pascalParser.IfBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(pascalParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#caseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseStatement(pascalParser.CaseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#readln}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadln(pascalParser.ReadlnContext ctx);
	/**
	 * Visit a parse tree produced by {@link pascalParser#writeln}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteln(pascalParser.WritelnContext ctx);
}