package dev.mathops.math.linalg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the SquareMatrix class.
 */
final class TestSquareMatrix {

    /** A small number used to compare results. */
    private static final double EPSILON = 0.000000000001;

    /** A small number used to compare results. */
    private static final double EPSILON2 = 0.0000001;

    /**
     * Constructs a new {@code TestSquareMatrix}.
     */
    TestSquareMatrix() {

        // No action
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Determinant of 1x1")
    void testDeterminant1() {

        final SquareMatrix test = new SquareMatrix(17.5);

        final double det = test.determinant();

        assertEquals(17.5, det, "Invalid determinant of 1x1 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Determinant of 2x2")
    void testDeterminant2() {

        final SquareMatrix test = new SquareMatrix(17.5, -12.4, 6.5, 3.2);

        final double det = test.determinant();

        // Wolfram alpha "det {{17.5, -12.4},{6.5, 3.2}}"
        assertEquals(136.6, det, EPSILON, "Invalid determinant of 2x2 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Determinant of 3x3")
    void testDeterminant3() {

        final SquareMatrix test = new SquareMatrix(17.5, -12.4, 9.1, 6.5, 3.2, -4.4, 1.9, -8.1, 15.4);

        final double det = test.determinant();

        // Wolfram alpha "det {{17.5, -12.4, 9.1},{6.5, 3.2, -4.4},{1.9, -8.1, 15.4}}"
        assertEquals(1049.161, det, EPSILON, "Invalid determinant of 3x3 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Determinant of 4x4")
    void testDeterminant4() {

        final SquareMatrix test1 = new SquareMatrix(
                0.0597058, 0.362764, 0.529353, 0.743410,
                0.0644189, 0.372940, 0.980050, 0.388748,
                0.8040580, 0.602346, 0.402664, 0.595672,
                0.5029760, 0.120310, 0.782990, 0.555785);

        final double det1 = test1.determinant();

        // Random matrix generated by Wolfram alpha, determinant found with MATLAB
        assertEquals(0.172673723499389, det1, EPSILON, "Invalid determinant of 4x4 matrix.");

        final SquareMatrix test2 = new SquareMatrix(
                0.896248, 0.230188, 0.687667, 0.966587,
                0.872015, 0.498319, 0.900730, 0.598575,
                0.544349, 0.887507, 0.651091, 0.807526,
                0.139408, 0.841727, 0.799754, 0.151275);

        final double det2 = test2.determinant();

        // Random matrix generated by Wolfram alpha, determinant found with MATLAB
        assertEquals(-0.101832948742812, det2, EPSILON, "Invalid determinant of 4x4 matrix.");

        final SquareMatrix test3 = new SquareMatrix(
                6.71406, 4.485290, 7.47972, 4.0236,
                2.60437, 8.002670, 9.81286, 1.58299,
                2.70144, 0.424962, 9.87810, 4.64747,
                3.88575, 8.418200, 7.03397, 8.30964);

        final double det3 = test3.determinant();

        // Random matrix generated by Wolfram alpha, determinant found with MATLAB
        assertEquals(2577.587267065266, det3, EPSILON, "Invalid determinant of 4x4 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Determinant of 10x10")
    void testDeterminant5() {

        final SquareMatrix test = new SquareMatrix(
                0.679907, 0.484257, 0.278555, 0.368329, 0.340532, 0.203652, 0.9462450, 0.2980480, 0.2384980, 0.9495700,
                0.658759, 0.796218, 0.457899, 0.104089, 0.476250, 0.167054, 0.4381900, 0.9569980, 0.7140110, 0.8149000,
                0.565297, 0.546658, 0.678315, 0.142485, 0.306676, 0.503536, 0.9168440, 0.6665350, 0.1443680, 0.3102970,
                0.840636, 0.113913, 0.541133, 0.402995, 0.343223, 0.392272, 0.4913240, 0.5704140, 0.1174820, 0.4048290,
                0.742019, 0.175687, 0.411406, 0.539525, 0.410085, 0.686180, 0.2139510, 0.5030780, 0.9936040, 0.7026920,
                0.495777, 0.416156, 0.639746, 0.455169, 0.528963, 0.699501, 0.6784500, 0.0216322, 0.6007570, 0.3136930,
                0.496401, 0.889872, 0.995780, 0.142381, 0.946278, 0.795033, 0.0296139, 0.4571320, 0.4784280, 0.3970320,
                0.925874, 0.118995, 0.232438, 0.286745, 0.195767, 0.632609, 0.9598510, 0.9420150, 0.0744246, 0.2023470,
                0.797212, 0.518764, 0.324527, 0.153161, 0.478742, 0.123190, 0.8362220, 0.7866780, 0.6889380, 0.4392460,
                0.704471, 0.744936, 0.939563, 0.923221, 0.113476, 0.593990, 0.2248800, 0.6873640, 0.5404410, 0.0217125);

        final double det = test.determinant();

        // Random matrix generated by Wolfram alpha, determinant found with MATLAB
        assertEquals(-0.001447354993894, det, EPSILON, "Invalid determinant of 10x10 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Inverse of 1x1")
    void testInverse1() {

        final SquareMatrix test = new SquareMatrix(17.5);

        final Optional<SquareMatrix> inv = test.inverse();

        final boolean found = inv.isPresent();
        assertTrue(found, "Failed to take inverse of invertible 1x1.");

        final SquareMatrix value = inv.get();

        assertEquals(1, value.n(), "Invalid size of inverse of 1x1 matrix.");
        assertEquals(1.0 / 17.5, value.get(0, 0), "Invalid entry in inverse of 1x1 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Inverse of 2x2")
    void testInverse2() {

        final SquareMatrix test = new SquareMatrix(17.5, -12.4, 6.5, 3.2);

        final Optional<SquareMatrix> inv = test.inverse();

        final boolean found = inv.isPresent();
        assertTrue(found, "Failed to take inverse of invertible 2x2.");

        final SquareMatrix value = inv.get();

        assertEquals(2, value.n(), "Invalid size of inverse of 2x2 matrix.");

        // Inverse from MATLAB
        assertEquals(0.023426061493411, value.get(0, 0), EPSILON, "Invalid entry in inverse of 2x2 matrix.");
        assertEquals(0.090775988286969, value.get(0, 1), EPSILON, "Invalid entry in inverse of 2x2 matrix.");
        assertEquals(-0.047584187408492, value.get(1, 0), EPSILON, "Invalid entry in inverse of 2x2 matrix.");
        assertEquals(0.128111273792094, value.get(1, 1), EPSILON, "Invalid entry in inverse of 2x2 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Inverse of 3x3")
    void testInverse3() {

        final SquareMatrix test = new SquareMatrix(17.5, -12.4, 9.1, 6.5, 3.2, -4.4, 1.9, -8.1, 15.4);

        final Optional<SquareMatrix> inv = test.inverse();

        final boolean found = inv.isPresent();
        assertTrue(found, "Failed to take inverse of invertible 3x3.");

        final SquareMatrix value = inv.get();

        assertEquals(3, value.n(), "Invalid size of inverse of 3x3 matrix.");

        // Inverse from MATLAB
        assertEquals(0.013000864500301, value.get(0, 0), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(0.111755965004418, value.get(0, 1), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(0.024247946692643, value.get(0, 2), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(-0.103377841913681, value.get(1, 0), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(0.240392084722936, value.get(1, 1), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(0.129770359363339, value.get(1, 2), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(-0.055978062470870, value.get(2, 0), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(0.112651919009570, value.get(2, 1), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
        assertEquals(0.130199273514742, value.get(2, 2), EPSILON, "Invalid entry in inverse of 3x3 matrix.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Inverse of 4x4")
    void tesInverse4() {

        final SquareMatrix test1 = new SquareMatrix(
                0.0597058, 0.362764, 0.529353, 0.743410,
                0.0644189, 0.372940, 0.980050, 0.388748,
                0.8040580, 0.602346, 0.402664, 0.595672,
                0.5029760, 0.120310, 0.782990, 0.555785);

        final Optional<SquareMatrix> inv1 = test1.inverse();

        final boolean found1 = inv1.isPresent();
        assertTrue(found1, "Failed to take inverse of invertible 4x4 1.");

        final SquareMatrix value1 = inv1.get();

        assertEquals(4, value1.n(), "Invalid size of inverse of 4x4 matrix 1.");

        // Inverse from MATLAB
        assertEquals(-1.064590903036879, value1.get(0, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-0.505651670035206, value1.get(0, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(0.762359542146653, value1.get(0, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(0.960591533524495, value1.get(0, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-0.035010190929165, value1.get(1, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(1.303148799422611, value1.get(1, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(1.332340633426323, value1.get(1, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-2.292626776961622, value1.get(1, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-0.684218948209392, value1.get(2, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(1.106304671219815, value1.get(2, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-0.383153837154997, value1.get(2, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(0.552039893928002, value1.get(2, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(1.934943088379566, value1.get(3, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-1.383046811607563, value1.get(3, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(-0.438545177945221, value1.get(3, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");
        assertEquals(0.648505670040663, value1.get(3, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 1.");

        final SquareMatrix test2 = new SquareMatrix(
                0.896248, 0.230188, 0.687667, 0.966587,
                0.872015, 0.498319, 0.900730, 0.598575,
                0.544349, 0.887507, 0.651091, 0.807526,
                0.139408, 0.841727, 0.799754, 0.151275);

        final Optional<SquareMatrix> inv2 = test2.inverse();

        final boolean found2 = inv2.isPresent();
        assertTrue(found2, "Failed to take inverse of invertible 4x4 2.");

        final SquareMatrix value2 = inv2.get();

        assertEquals(4, value2.n(), "Invalid size of inverse of 4x4 matrix 2.");

        // Inverse from MATLAB
        assertEquals(-3.097075693496185, value2.get(0, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(3.981496944993943, value2.get(0, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(1.294426421796252, value2.get(0, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(-2.874992042226355, value2.get(0, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(-2.394062322651279, value2.get(1, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(1.342293628207493, value2.get(1, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(2.086434426127472, value2.get(1, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(-1.151835641267765, value2.get(1, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(2.556937657177695, value2.get(2, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(-1.557608531521978, value2.get(2, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(-2.427187437054801, value2.get(2, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(2.782084216245026, value2.get(2, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(2.657298997578705, value2.get(3, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(-2.903279863685973, value2.get(3, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(0.029689457766022, value2.get(3, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");
        assertEquals(0.960800324979399, value2.get(3, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 2.");

        final SquareMatrix test3 = new SquareMatrix(
                6.71406, 4.485290, 7.47972, 4.0236,
                2.60437, 8.002670, 9.81286, 1.58299,
                2.70144, 0.424962, 9.87810, 4.64747,
                3.88575, 8.418200, 7.03397, 8.30964);

        final Optional<SquareMatrix> inv3 = test3.inverse();

        final boolean found3 = inv3.isPresent();
        assertTrue(found3, "Failed to take inverse of invertible 4x4 3.");

        final SquareMatrix value3 = inv3.get();

        assertEquals(4, value3.n(), "Invalid size of inverse of 4x4 matrix 3.");

        // Inverse from MATLAB
        assertEquals(0.239617495724427, value3.get(0, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.064092210604257, value3.get(0, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.072863958831062, value3.get(0, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.063063449750383, value3.get(0, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.021294109245975, value3.get(1, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(0.085667337848679, value3.get(1, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.107518702948860, value3.get(1, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(0.054124894124747, value3.get(1, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.036636618427747, value3.get(2, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(0.067425808669958, value3.get(2, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(0.097232541460847, value3.get(2, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.049485730118707, value3.get(2, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.059465240207097, value3.get(3, 0), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(-0.113890564637274, value3.get(3, 1), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(0.060690269798997, value3.get(3, 2), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
        assertEquals(0.136888692798929, value3.get(3, 3), EPSILON, "Invalid entry in inverse of 4x4 matrix 3.");
    }

    /**
     * A test case.
     */
    @Test
    @DisplayName("Inverse of 10x10")
    void testInverse5() {

        final SquareMatrix test = new SquareMatrix(
                0.679907, 0.484257, 0.278555, 0.368329, 0.340532, 0.203652, 0.9462450, 0.2980480, 0.2384980, 0.9495700,
                0.658759, 0.796218, 0.457899, 0.104089, 0.476250, 0.167054, 0.4381900, 0.9569980, 0.7140110, 0.8149000,
                0.565297, 0.546658, 0.678315, 0.142485, 0.306676, 0.503536, 0.9168440, 0.6665350, 0.1443680, 0.3102970,
                0.840636, 0.113913, 0.541133, 0.402995, 0.343223, 0.392272, 0.4913240, 0.5704140, 0.1174820, 0.4048290,
                0.742019, 0.175687, 0.411406, 0.539525, 0.410085, 0.686180, 0.2139510, 0.5030780, 0.9936040, 0.7026920,
                0.495777, 0.416156, 0.639746, 0.455169, 0.528963, 0.699501, 0.6784500, 0.0216322, 0.6007570, 0.3136930,
                0.496401, 0.889872, 0.995780, 0.142381, 0.946278, 0.795033, 0.0296139, 0.4571320, 0.4784280, 0.3970320,
                0.925874, 0.118995, 0.232438, 0.286745, 0.195767, 0.632609, 0.9598510, 0.9420150, 0.0744246, 0.2023470,
                0.797212, 0.518764, 0.324527, 0.153161, 0.478742, 0.123190, 0.8362220, 0.7866780, 0.6889380, 0.4392460,
                0.704471, 0.744936, 0.939563, 0.923221, 0.113476, 0.593990, 0.2248800, 0.6873640, 0.5404410, 0.0217125);

        final Optional<SquareMatrix> inv = test.inverse();

        final boolean found = inv.isPresent();
        assertTrue(found, "Failed to take inverse of invertible 10x10.");

        final SquareMatrix value = inv.get();

        assertEquals(10, value.n(), "Invalid size of inverse of 10x10 matrix 3.");

        // Inverse from MATLAB
        assertEquals(18.419947372167957, value.get(0, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-47.103951195890545, value.get(0, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(22.479664442469154, value.get(0, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-16.957173064894675, value.get(0, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(24.616434947844425, value.get(0, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-42.847069690338984, value.get(0, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(16.559493034408327, value.get(0, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-11.038316143872319, value.get(0, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(28.415172442503643, value.get(0, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(4.793920100073795, value.get(0, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(7.762640338822221, value.get(1, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-17.296859837373436, value.get(1, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(7.903456621850451, value.get(1, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-8.190527969455399, value.get(1, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(8.592877087864782, value.get(1, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-16.104360732304745, value.get(1, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(6.769628140853469, value.get(1, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-3.478323595930396, value.get(1, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(10.390921366127595, value.get(1, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(2.439286389399220, value.get(1, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(0.270899158896404, value.get(2, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-3.152000333522532, value.get(2, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(3.763291680196357, value.get(2, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(0.506939455808577, value.get(2, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(2.035243980585910, value.get(2, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-2.884582005241293, value.get(2, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(0.474245276117609, value.get(2, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-2.646652171252053, value.get(2, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(1.724050241275711, value.get(2, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(0.140857567877375, value.get(2, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(-12.962622521650902, value.get(3, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(34.859790067453027, value.get(3, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-18.974769524079743, value.get(3, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(13.275725952112733, value.get(3, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-19.063055203006527, value.get(3, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(32.232867959419330, value.get(3, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-12.247527994529422, value.get(3, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(8.861552273477370, value.get(3, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-20.882762047715033, value.get(3, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-2.692329642723751, value.get(3, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(-13.402416232342375, value.get(4, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(34.257053011737312, value.get(4, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-18.966580473917677, value.get(4, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(13.585488101075164, value.get(4, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-19.181676132671512, value.get(4, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(32.010828363875937, value.get(4, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-11.125326145448417, value.get(4, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(8.762694928896950, value.get(4, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-19.669136381365842, value.get(4, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-3.833466308939522, value.get(4, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(3.413979990840862, value.get(5, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-8.474799912136067, value.get(5, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(4.418935899397789, value.get(5, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-4.446934282092306, value.get(5, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(5.109096445342912, value.get(5, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-7.598094196615563, value.get(5, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(3.382237388418593, value.get(5, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-0.856399106502871, value.get(5, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(3.873985469352778, value.get(5, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(0.714580605100918, value.get(5, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(-3.728258696782697, value.get(6, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(9.428664221775385, value.get(6, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-4.117657262523487, value.get(6, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(3.417018742348539, value.get(6, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-5.298096708594423, value.get(6, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(9.332003204566155, value.get(6, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-3.839021128161300, value.get(6, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(2.249188918191928, value.get(6, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-5.370870531893785, value.get(6, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-1.151887308838064, value.get(6, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(-10.971898867855145, value.get(7, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(27.437599184480170, value.get(7, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-13.209694319123848, value.get(7, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(10.171599724897025, value.get(7, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-14.186062963724590, value.get(7, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(23.990558449164332, value.get(7, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-9.467873433592892, value.get(7, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(6.891599549722567, value.get(7, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-16.150765026102704, value.get(7, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-2.655968875391392, value.get(7, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(1.542163981142340, value.get(8, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-5.642655576108400, value.get(8, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(3.339226420726898, value.get(8, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-2.535814239410084, value.get(8, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(3.779862820695903, value.get(8, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-4.835146319856756, value.get(8, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(1.479312955994609, value.get(8, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-1.959192769036644, value.get(8, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(4.059843823657384, value.get(8, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(0.494222511973670, value.get(8, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");

        assertEquals(-0.300168723199369, value.get(9, 0), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(2.892958648646306, value.get(9, 1), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-0.605402488793265, value.get(9, 2), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(1.141289406575477, value.get(9, 3), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-0.644572095176137, value.get(9, 4), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(1.770291616091229, value.get(9, 5), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-1.007494379385782, value.get(9, 6), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(0.145417568058790, value.get(9, 7), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-2.425775435656349, value.get(9, 8), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
        assertEquals(-0.594626957894884, value.get(9, 9), EPSILON, "Invalid entry in inverse of 10x10 matrix.");
    }
}
